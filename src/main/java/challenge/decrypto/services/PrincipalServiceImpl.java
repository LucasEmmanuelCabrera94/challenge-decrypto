package challenge.decrypto.services;

import challenge.decrypto.entities.Market;
import challenge.decrypto.entities.Principal;
import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequest;
import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.repositories.PrincipalRepository;
import challenge.decrypto.services.interfaces.PrincipalService;
import challenge.decrypto.utils.exceptions.NotFoundException;
import challenge.decrypto.utils.mappers.PrincipalMapper;

import java.util.HashSet;
import java.util.List;

public class PrincipalServiceImpl implements PrincipalService {
    private final PrincipalRepository principalRepository;
    private final MarketRepository marketRepository;

    public PrincipalServiceImpl(PrincipalRepository principalRepository,MarketRepository marketRepository) {
        this.principalRepository = principalRepository;
        this.marketRepository = marketRepository;
    }

    @Override
    public PrincipalDTO createPrincipal(PrincipalRequest request) {
        if (principalRepository.existsByDescription(request.getDescription())) {
            throw new NotFoundException("el comitente ya existe"); //TODO : Mejorar esto mas adelante
        }

        Principal principal = Principal.builder()
                .description(request.getDescription())
                .build();

        if(request.getMarkets() == null || request.getMarkets().isEmpty()) {
            throw new NotFoundException("Debe seleccionar al menos un mercado"); //TODO : Mejorar esto mas adelante
        }
        for (Long marketId : request.getMarkets()) {
            Market market = marketRepository.findById(marketId).orElseThrow(() -> new NotFoundException("Market no encontrado"));//TODO : Mejorar esto mas adelante

            if (principal.getMarkets() == null) {
                principal.setMarkets(new HashSet<>());
            }
            if (principal.getMarkets().isEmpty() || !principal.getMarkets().contains(market)) {
                principal.getMarkets().add(market);
            }
        }

        Principal response = principalRepository.save(principal);
        return PrincipalMapper.toDTO(response);
    }

    @Override
    public List<PrincipalDTO> getAllPrincipals() {
        List<Principal> principalList = principalRepository.findAll();
        return principalList.stream().map(PrincipalMapper::toDTO).toList();
    }
}
