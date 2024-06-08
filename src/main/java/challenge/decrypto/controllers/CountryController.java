package challenge.decrypto.controllers;

import challenge.decrypto.models.countries.CountryDTO;
import challenge.decrypto.models.exceptions.ErrorDTO;
import challenge.decrypto.services.interfaces.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paises")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @Operation(
            description = "Endpoint para crear un nuevo pais. " ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pais creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Los datos ingresados son incorrectos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
          }
    )
    @PostMapping
    public CountryDTO createCountry(@RequestBody CountryDTO request) {
        return countryService.createCountry(request);
    }

    @Operation(
            description = "Endpoint para buscar todos los paises." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pais creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pais no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @GetMapping
    public List<CountryDTO> getAll() {
        return countryService.getAllCountries();
    }

    @Operation(
            description = "Endpoint para buscar un pais por Id." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pais creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pais no econtrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable Long id) {
        CountryDTO country = countryService.getCountryById(id);
        if (country != null) {
            return ResponseEntity.ok(country);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}