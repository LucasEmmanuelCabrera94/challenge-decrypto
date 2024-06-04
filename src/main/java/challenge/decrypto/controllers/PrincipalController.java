package challenge.decrypto.controllers;

import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequest;
import challenge.decrypto.services.interfaces.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comitentes")
public class PrincipalController {
    @Autowired
    private PrincipalService principalService;

    @PostMapping
    public PrincipalDTO createPrincipal(@RequestBody PrincipalRequest request) {
        return principalService.createPrincipal(request);
    }

    @GetMapping
    public List<PrincipalDTO> getAll() {
        return principalService.getAllPrincipals();
    }
}
