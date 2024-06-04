package challenge.decrypto.services;

import challenge.decrypto.entities.Principal;
import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequest;
import challenge.decrypto.repositories.PrincipalRepository;
import challenge.decrypto.services.interfaces.PrincipalService;
import challenge.decrypto.utils.mappers.PrincipalMapper;

import java.util.List;

public class PrincipalServiceImpl implements PrincipalService {
    private final PrincipalRepository principalRepository;

    public PrincipalServiceImpl(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    @Override
    public PrincipalDTO createPrincipal(PrincipalRequest request) {
        if (principalRepository.existsByDescription(request.getDescription())) {
            throw new RuntimeException("el comitente ya existe"); //TODO : Mejorar esto mas adelante
        }

        Principal principal = Principal.builder()
                .description(request.getDescription())
                .build();

        Principal response = principalRepository.save(principal);
        return PrincipalMapper.toDTO(response);
    }

    @Override
    public List<PrincipalDTO> getAllPrincipals() {
        List<Principal> principalList = principalRepository.findAll();
        return principalList.stream().map(PrincipalMapper::toDTO).toList();
    }
}
