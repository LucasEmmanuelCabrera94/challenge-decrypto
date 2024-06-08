package challenge.decrypto.controllers;

import challenge.decrypto.models.countries.CountryDTO;
import challenge.decrypto.services.interfaces.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryController.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateCountry() throws Exception {
        CountryDTO countryDTO = CountryDTO.builder().id(1L).name("Argentina").build();

        Mockito.when(countryService.createCountry(Mockito.any(CountryDTO.class))).thenReturn(countryDTO);

        mockMvc.perform(post("/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(countryDTO)));
    }

    @Test
    public void testGetAllCountries() throws Exception {
        CountryDTO country1 = CountryDTO.builder().id(1L).name("Argentina").build();
        CountryDTO country2 = CountryDTO.builder().id(2L).name("Brasil").build();

        List<CountryDTO> countries = Arrays.asList(country1, country2);

        Mockito.when(countryService.getAllCountries()).thenReturn(countries);

        mockMvc.perform(get("/paises")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(countries)));
    }

    @Test
    public void testGetCountryById() throws Exception {
        CountryDTO countryDTO = CountryDTO.builder().id(1L).name("Argentina").build();

        Mockito.when(countryService.getCountryById(1L)).thenReturn(countryDTO);

        mockMvc.perform(get("/paises/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(countryDTO)));
    }

    @Test
    public void testGetCountryByIdNotFound() throws Exception {
        Mockito.when(countryService.getCountryById(1L)).thenReturn(null);

        mockMvc.perform(get("/paises/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
