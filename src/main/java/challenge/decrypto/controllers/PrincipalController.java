package challenge.decrypto.controllers;

import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequestDTO;
import challenge.decrypto.models.principals.UpdatePrincipalRequestDTO;
import challenge.decrypto.services.interfaces.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comitentes")
public class PrincipalController {
    @Autowired
    private PrincipalService principalService;

    @PostMapping
    public PrincipalDTO createPrincipal(@RequestBody PrincipalRequestDTO request) {
        return principalService.createPrincipal(request);
    }

    @GetMapping
    public List<PrincipalDTO> getAll() {
        return principalService.getAllPrincipals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrincipalDTO> getById(@PathVariable Long id) {
        PrincipalDTO principal = principalService.getPrincipalById(id);
        if (principal != null) {
            return ResponseEntity.ok(principal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrincipalDTO> updatePrincipal(@PathVariable Long id, @RequestBody UpdatePrincipalRequestDTO request) {
        PrincipalDTO updatedPrincipal = principalService.updatePrincipal(id, request);
        if (updatedPrincipal != null) {
            return ResponseEntity.ok(updatedPrincipal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrincipal(@PathVariable Long id) {
        boolean deleted = principalService.deletePrincipal(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}