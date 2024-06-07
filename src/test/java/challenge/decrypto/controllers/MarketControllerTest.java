package challenge.decrypto.controllers;

import challenge.decrypto.models.countries.CountryDTO;
import challenge.decrypto.models.markets.MarketDTO;
import challenge.decrypto.models.markets.MarketRequestDTO;
import challenge.decrypto.models.markets.UpdateMarketRequestDTO;
import challenge.decrypto.services.interfaces.MarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarketController.class)
public class MarketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketService marketService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_Success() throws Exception {
        MarketDTO market1 = MarketDTO.builder()
                .id(1L)
                .code("MKT1")
                .description("Mercado 1")
                .country(null)
                .principals(null).build();

        MarketDTO market2 = MarketDTO.builder()
                .id(2L)
                .code("MKT2")
                .description("Mercado 2")
                .country(null)
                .principals(null).build();
        List<MarketDTO> marketList = Arrays.asList(market1, market2);

        when(marketService.getAllMarkets()).thenReturn(marketList);

        mockMvc.perform(MockMvcRequestBuilders.get("/mercados"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].code", is("MKT1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", is("Mercado 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].code", is("MKT2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", is("Mercado 2")));
    }

    @Test
    public void testGetMarketById_Success() throws Exception {
        MarketDTO market = MarketDTO.builder()
                .id(1L)
                .code("MKT1")
                .description("Mercado 1")
                .country(null)
                .principals(null).build();
        when(marketService.getMarketById(1L)).thenReturn(market);

        mockMvc.perform(MockMvcRequestBuilders.get("/mercados/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is("MKT1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Mercado 1")));
    }

    @Test
    public void testCreateMarket_Success() throws Exception {
        MarketRequestDTO requestDTO = MarketRequestDTO.builder()
                .code("MKT1")
                .description("Mercado 1")
                .country("Argentina")
                .build();

        MarketDTO createdMarket = MarketDTO.builder()
                .id(1L)
                .code("MKT1")
                .principals(null)
                .country(CountryDTO.builder().id(1L).name("Argentina").build())
                .description("Mercado 1")
                .build();
        when(marketService.createMarket(any(MarketRequestDTO.class))).thenReturn(createdMarket);

        mockMvc.perform(post("/mercados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.code", is("MKT1")))
                .andExpect(jsonPath("$.description", is("Mercado 1")))
                .andExpect(jsonPath("$.country.name", is("Argentina")));

    }


    @Test
    public void testDeleteMarket_Success() throws Exception {
        when(marketService.deleteMarket(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/mercados/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Se elimino correctamente el mercado con el id: 1"));
    }

    @Test
    public void testUpdateMarket_Success() throws Exception {
        UpdateMarketRequestDTO requestDTO = new UpdateMarketRequestDTO();
        requestDTO.setCode("MKT1");
        requestDTO.setDescription("Mercado Actualizado");
        requestDTO.setCountry("Argentina");

        MarketDTO updatedMarket = MarketDTO.builder()
                .id(1L)
                .code("MKT1")
                .principals(null)
                .country(CountryDTO.builder().id(1L).name("Argentina").build())
                .description("Mercado Actualizado")
                .build();

        Mockito.when(marketService.updateMarket(eq(1L), any(UpdateMarketRequestDTO.class))).thenReturn(updatedMarket);

        mockMvc.perform(put("/mercados/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.code", is("MKT1")))
                .andExpect(jsonPath("$.description", is("Mercado Actualizado")))
                .andExpect(jsonPath("$.country.name", is("Argentina")));
    }

}
