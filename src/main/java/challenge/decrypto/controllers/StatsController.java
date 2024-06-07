package challenge.decrypto.controllers;

import challenge.decrypto.models.exceptions.ErrorDTO;
import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.stats.StatsDTO;
import challenge.decrypto.services.interfaces.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    private StatsService statsService;

    @Operation(
            description = "Endpoint para devuelver cifras totalizadoras de distribución de comitentes por país y mercado" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comitente creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrincipalDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Comitente no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @GetMapping
    public List<StatsDTO> getStats() {
        return statsService.getStats();
    }
}
