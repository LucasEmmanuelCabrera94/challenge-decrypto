package challenge.decrypto.services;


import challenge.decrypto.entities.Country;
import challenge.decrypto.entities.Market;
import challenge.decrypto.entities.Principal;
import challenge.decrypto.models.stats.StatsDTO;
import challenge.decrypto.repositories.MarketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceImplTest {
    @Mock
    private MarketRepository marketRepository;

    @InjectMocks
    private StatsServiceImpl statsService;

    @Test
    public void testGetStats_Success() {
        Country argentina = Country.builder().name("Argentina").build();
        Country uruguay = Country.builder().name("Uruguay").build();

        Principal principal1 = Principal.builder().description("Comitente 1").build();
        Principal principal2 = Principal.builder().description("Comitente 2").build();
        Principal principal3 = Principal.builder().description("Comitente 3").build();

        Market market1 = Market.builder()
                .code("MAE")
                .description("Mercado 1")
                .country(argentina)
                .principals(new HashSet<>(Arrays.asList(principal1, principal2)))
                .build();
        Market market2 = Market.builder()
                .code("ROFEX")
                .description("Mercado 2")
                .country(argentina)
                .principals(new HashSet<>(Arrays.asList(principal1, principal1)))
                .build();
        Market market3 = Market.builder()
                .code("UFEX")
                .description("Mercado 3")
                .country(uruguay)
                .principals(new HashSet<>(Arrays.asList(principal1, principal3)))
                .build();

        when(marketRepository.findAll()).thenReturn(Arrays.asList(market1, market2, market3));

        List<StatsDTO> stats = statsService.getStats();

        assertEquals(2, stats.size());

        StatsDTO argentinaStats = stats.stream().filter(s -> s.getCountry().equals("Argentina")).findFirst().orElse(null);
        StatsDTO uruguayStats = stats.stream().filter(s -> s.getCountry().equals("Uruguay")).findFirst().orElse(null);

        assertNotNull(argentinaStats);
        assertEquals(2, argentinaStats.getMarkets().size());

        assertNotNull(uruguayStats);
        assertEquals(1, uruguayStats.getMarkets().size());

        verify(marketRepository, times(1)).findAll();
    }

    @Test
    public void testGetStats_MarketsWithoutPrincipals() {
        Country argentina = Country.builder().name("Argentina").build();
        Country uruguay = Country.builder().name("Uruguay").build();

        Principal principal1 = Principal.builder().description("Comitente 1").build();

        Market market1 = Market.builder().code("MAE").description("Mercado 1").principals(new HashSet<>()).country(argentina).build();
        Market market2 = Market.builder().code("ROFEX").principals(new HashSet<>()).description("Mercado 2").country(argentina).build();
        Market market3 = Market.builder().code("UFEX").description("Mercado 3").principals(new HashSet<>(Arrays.asList(principal1))).country(uruguay).build();

        when(marketRepository.findAll()).thenReturn(Arrays.asList(market1, market2, market3));

        List<StatsDTO> stats = statsService.getStats();

        assertEquals(2, stats.size());

        StatsDTO argentinaStats = stats.stream().filter(s -> s.getCountry().equals("Argentina")).findFirst().orElse(null);
        StatsDTO uruguayStats = stats.stream().filter(s -> s.getCountry().equals("Uruguay")).findFirst().orElse(null);

        assertNotNull(argentinaStats);
        assertEquals(2, argentinaStats.getMarkets().size());
        assertEquals("0", argentinaStats.getMarkets().get(0).getPrincipalQuantity());
        assertEquals("0", argentinaStats.getMarkets().get(1).getPrincipalQuantity());

        assertNotNull(uruguayStats);
        assertEquals(1, uruguayStats.getMarkets().size());
        assertEquals("1", uruguayStats.getMarkets().get(0).getPrincipalQuantity());

        verify(marketRepository, times(1)).findAll();
    }


}
