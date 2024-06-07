package challenge.decrypto.services;

import challenge.decrypto.entities.Country;
import challenge.decrypto.entities.Market;
import challenge.decrypto.entities.Principal;
import challenge.decrypto.models.markets.MarketDTO;
import challenge.decrypto.models.markets.MarketRequestDTO;
import challenge.decrypto.models.markets.UpdateMarketRequestDTO;
import challenge.decrypto.repositories.CountryRepository;
import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.repositories.PrincipalRepository;
import challenge.decrypto.services.interfaces.MarketService;
import challenge.decrypto.utils.exceptions.BadRequestException;
import challenge.decrypto.utils.exceptions.NotFoundException;
import challenge.decrypto.utils.mappers.MarketMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MarketServiceImpl implements MarketService {
    private final MarketRepository marketRepository;
    private final PrincipalRepository principalRepository;
    private final CountryRepository countryRepository;

    public MarketServiceImpl(MarketRepository marketRepository, PrincipalRepository principalRepository,CountryRepository countryRepository){
        this.marketRepository = marketRepository;
        this.principalRepository = principalRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public MarketDTO createMarket(MarketRequestDTO request) {
        validateMarketRequestDTO(request);

        Country country = countryRepository.findByName(request.getCountry()).orElseThrow(() -> new NotFoundException("Pais no encontrado"));
        Set<Principal> principals = fetchPrincipals(request.getPrincipals());

        Market market = Market.builder()
                .code(request.getCode())
                .description(request.getDescription())
                .country(country)
                .principals(principals)
                .build();

        marketRepository.save(market);
        return MarketMapper.toDTO(market);
    }

    @Override
    public List<MarketDTO> getAllMarkets() {
        List<Market> marketList = marketRepository.findAll();
        return marketList.stream()
                .map(MarketMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MarketDTO getMarketById(Long id) {
        Market market = marketRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Mercado no encontrado"));
        return MarketMapper.toDTO(market);
    }

    @Override
    public MarketDTO updateMarket(Long id, UpdateMarketRequestDTO request) {
        Market market = marketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mercado no encontrado"));

        if (request.getDescription() != null) {
            market.setDescription(request.getDescription());
        }

        if (request.getCode() != null) {
            market.setCode(request.getCode());
        }

        if (request.getCountry() != null) {
            Country country = countryRepository.findByName(request.getCountry()).orElseThrow(()
                    -> new NotFoundException("Pais no encontrado"));

            market.setCountry(country);
        }

        Market updatedMarket = marketRepository.save(market);
        return MarketMapper.toDTO(updatedMarket);
    }

    @Override
    public boolean deleteMarket(Long id) {
        if (!marketRepository.existsById(id)) {
            throw new NotFoundException("Mercado no encontrado");
        }
        marketRepository.deleteById(id);
        return true;
    }

    private void validateMarketRequestDTO(MarketRequestDTO requestDTO) {
        if (requestDTO.getCode() == null || requestDTO.getCode().isEmpty()) {
            throw new BadRequestException("El código del mercado no puede estar vacío");
        }

        if (requestDTO.getDescription() == null || requestDTO.getDescription().isEmpty()) {
            throw new BadRequestException("La descripción del mercado no puede estar vacía");
        }

        if (requestDTO.getCountry() == null) {
            throw new BadRequestException("Se debe proporcionar el país del mercado");
        }

        if (requestDTO.getPrincipals() == null || requestDTO.getPrincipals().isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos un comitente");
        }
    }

    private Set<Principal> fetchPrincipals(List<Long> principalsIds) {
        Set<Principal> principals = new HashSet<>();
        for (Long principalId : principalsIds) {
            Principal principal = principalRepository.findById(principalId)
                    .orElseThrow(() -> new NotFoundException("Comitente no encontrado"));
            principals.add(principal);
        }
        return principals;
    }
}
