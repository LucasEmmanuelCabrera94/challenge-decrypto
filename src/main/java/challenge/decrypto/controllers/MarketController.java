package challenge.decrypto.controllers;

import challenge.decrypto.models.exceptions.ErrorDTO;
import challenge.decrypto.models.markets.MarketDTO;
import challenge.decrypto.models.markets.MarketRequestDTO;
import challenge.decrypto.models.markets.UpdateMarketRequestDTO;
import challenge.decrypto.services.interfaces.MarketService;
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
@RequestMapping("/mercados")
public class MarketController {
    @Autowired
    private MarketService marketService;

    @Operation(
            description = "Endpoint para crear un nuevo mercado. " ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Mercado creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Los datos ingresados son incorrectos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Comitente no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @PostMapping
    public MarketDTO createMarket(@RequestBody MarketRequestDTO request) {
        return marketService.createMarket(request);
    }

    @Operation(
            description = "Endpoint para buscar todos los mercados " ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Mercado creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Mercado no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @GetMapping
    public List<MarketDTO> getAll() {
        return marketService.getAllMarkets();
    }

    @Operation(
            description = "Endpoint para buscar un mercado por Id." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Mercado creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Mercado no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MarketDTO> getMarketById(@PathVariable Long id) {
        MarketDTO market = marketService.getMarketById(id);
        if (market != null) {
            return ResponseEntity.ok(market);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            description = "Endpoint para actualizar un mercado por Id." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Mercado creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Falta valor de la entidad.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Mercado no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MarketDTO> updateMarket(@PathVariable Long id, @RequestBody UpdateMarketRequestDTO request) {
        MarketDTO updateMarket = marketService.updateMarket(id, request);
        if (updateMarket != null) {
            return ResponseEntity.ok(updateMarket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            description = "Endpoint para eliminar un mercado por Id." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Mercado creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Mercado no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMarket(@PathVariable Long id) {
        boolean deleted = marketService.deleteMarket(id);
        if (deleted) {
            return ResponseEntity.ok("Se elimino correctamente el mercado con el id: " + id );
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}