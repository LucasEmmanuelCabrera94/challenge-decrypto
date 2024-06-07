package challenge.decrypto.services.interfaces;


import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequestDTO;
import challenge.decrypto.models.principals.UpdatePrincipalRequestDTO;

import java.util.List;

public interface PrincipalService {
    PrincipalDTO createPrincipal(PrincipalRequestDTO request);

    List<PrincipalDTO> getAllPrincipals();
    PrincipalDTO getPrincipalById(Long id);
    PrincipalDTO updatePrincipal(Long id, UpdatePrincipalRequestDTO request);
    boolean deletePrincipal(Long id);
}
