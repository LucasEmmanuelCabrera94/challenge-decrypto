package challenge.decrypto.controllers;

import challenge.decrypto.models.exceptions.ErrorDTO;
import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequestDTO;
import challenge.decrypto.models.principals.UpdatePrincipalRequestDTO;
import challenge.decrypto.services.interfaces.PrincipalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            description = "Endpoint para crear un nuevo comitente. " ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comitente creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrincipalDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Los datos ingresados son incorrectos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Mercado no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @PostMapping
    public PrincipalDTO createPrincipal(@RequestBody PrincipalRequestDTO request) {
        return principalService.createPrincipal(request);
    }

    @Operation(
            description = "Endpoint para buscar todos los comitentes " ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comitente creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrincipalDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Comitente no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @GetMapping
    public List<PrincipalDTO> getAll() {
        return principalService.getAllPrincipals();
    }

    @Operation(
            description = "Endpoint para buscar un comitente por Id." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comitente creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrincipalDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Comitente no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PrincipalDTO> getById(@PathVariable Long id) {
        PrincipalDTO principal = principalService.getPrincipalById(id);
        if (principal != null) {
            return ResponseEntity.ok(principal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            description = "Endpoint para actualizar un comitente por Id." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comitente creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrincipalDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Debe proporcionar una descripción",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Comitente no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PrincipalDTO> updatePrincipal(@PathVariable Long id, @RequestBody UpdatePrincipalRequestDTO request) {
        PrincipalDTO updatedPrincipal = principalService.updatePrincipal(id, request);
        if (updatedPrincipal != null) {
            return ResponseEntity.ok(updatedPrincipal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            description = "Endpoint para eliminar un comitente por Id." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comitente creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrincipalDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Comitente no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
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