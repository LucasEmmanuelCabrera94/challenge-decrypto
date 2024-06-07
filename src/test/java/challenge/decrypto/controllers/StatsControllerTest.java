package challenge.decrypto.controllers;

import challenge.decrypto.models.markets.MarketStatsDTO;
import challenge.decrypto.models.stats.StatsDTO;
import challenge.decrypto.services.interfaces.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StatsController.class)
public class StatsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    private List<StatsDTO> statsList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        statsList = new ArrayList<>();
        List<MarketStatsDTO> marketStatsList = new ArrayList<>();
        marketStatsList.add(MarketStatsDTO.builder().marketCode("MAE").principalQuantity("80.75").build());
        marketStatsList.add(MarketStatsDTO.builder().marketCode("ROFEX").principalQuantity("2.00").build());
        StatsDTO argentinaStats = StatsDTO.builder().country("Argentina").markets(marketStatsList).build();

        List<MarketStatsDTO> marketStatsListUruguay = new ArrayList<>();
        marketStatsListUruguay.add(MarketStatsDTO.builder().marketCode("UFEX").principalQuantity("17.25").build());
        StatsDTO uruguayStats = StatsDTO.builder().country("Uruguay").markets(marketStatsListUruguay).build();

        statsList.add(argentinaStats);
        statsList.add(uruguayStats);
    }

    @Test
    public void testGetStats_Success() throws Exception {
        when(statsService.getStats()).thenReturn(statsList);

        String expectedJson = "[{" +
                "\"country\":\"Argentina\"," +
                "\"markets\":[" +
                "{\"marketCode\":\"MAE\",\"principalQuantity\":\"80.75\"}," +
                "{\"marketCode\":\"ROFEX\",\"principalQuantity\":\"2.00\"}" +
                "]" +
                "}," +
                "{" +
                "\"country\":\"Uruguay\"," +
                "\"markets\":[" +
                "{\"marketCode\":\"UFEX\",\"principalQuantity\":\"17.25\"}" +
                "]" +
                "}]";

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }
}
