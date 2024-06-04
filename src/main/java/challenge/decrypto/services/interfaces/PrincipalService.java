package challenge.decrypto.services.interfaces;


import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequest;

import java.util.List;

public interface PrincipalService {
    PrincipalDTO createPrincipal(PrincipalRequest request);

    List<PrincipalDTO> getAllPrincipals();
}
