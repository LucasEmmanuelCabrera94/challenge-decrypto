package challenge.decrypto.services;

import challenge.decrypto.entities.Market;
import challenge.decrypto.entities.Principal;
import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequestDTO;
import challenge.decrypto.models.principals.UpdatePrincipalRequestDTO;
import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.repositories.PrincipalRepository;
import challenge.decrypto.services.interfaces.PrincipalService;
import challenge.decrypto.utils.exceptions.BadRequestException;
import challenge.decrypto.utils.exceptions.NotFoundException;
import challenge.decrypto.utils.mappers.PrincipalMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PrincipalServiceImpl implements PrincipalService {
    private final PrincipalRepository principalRepository;
    private final MarketRepository marketRepository;

    public PrincipalServiceImpl(PrincipalRepository principalRepository, MarketRepository marketRepository) {
        this.principalRepository = principalRepository;
        this.marketRepository = marketRepository;
    }

    @Override
    public PrincipalDTO createPrincipal(PrincipalRequestDTO request) {
        validatePrincipalDescription(request.getDescription());

        Principal principal = Principal.builder().build();
        principal.setDescription(request.getDescription());
        Set<Market> markets = validateAndFetchMarkets(request.getMarkets());

        principal.setMarkets(markets);

        Principal savedPrincipal = principalRepository.save(principal);
        return PrincipalMapper.toDTO(savedPrincipal);
    }

    @Override
    public List<PrincipalDTO> getAllPrincipals() {
        List<Principal> principalList = principalRepository.findAllWithMarkets();
        return principalList.stream()
                .map(PrincipalMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrincipalDTO getPrincipalById(Long id) {
        Principal principal = principalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comitente no encontrado"));
        return PrincipalMapper.toDTO(principal);
    }

    @Override
    public PrincipalDTO updatePrincipal(Long id, UpdatePrincipalRequestDTO request) {
        Principal principal = principalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comitente no encontrado"));

        if (request.getDescription() != null) {
            principal.setDescription(request.getDescription());
        } else {
            throw new BadRequestException("Debe proporcionar una descripción");
        }

        Principal updatedPrincipal = principalRepository.save(principal);
        return PrincipalMapper.toDTO(updatedPrincipal);
    }

    @Override
    public boolean deletePrincipal(Long id) {
        if (!principalRepository.existsById(id)) {
            throw new NotFoundException("Comitente no encontrado");
        }
        principalRepository.deleteById(id);
        return true;
    }

    private void validatePrincipalDescription(String description) {
        if (principalRepository.existsByDescription(description)) {
            throw new BadRequestException("El comitente ya existe");
        }
    }

    private Set<Market> validateAndFetchMarkets(List<Long> marketIds) {
        if (marketIds == null || marketIds.isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos un mercado");
        }

        Set<Market> markets = new HashSet<>();
        for (Long marketId : marketIds) {
            Market market = marketRepository.findById(marketId)
                    .orElseThrow(() -> new NotFoundException("Market no encontrado"));
            markets.add(market);
        }
        return markets;
    }
}
