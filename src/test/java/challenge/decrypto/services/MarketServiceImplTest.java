package challenge.decrypto.services;

import challenge.decrypto.entities.Country;
import challenge.decrypto.entities.Market;
import challenge.decrypto.entities.Principal;
import challenge.decrypto.models.markets.MarketDTO;
import challenge.decrypto.models.markets.MarketRequestDTO;
import challenge.decrypto.models.markets.UpdateMarketRequestDTO;
import challenge.decrypto.repositories.CountryRepository;
import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.repositories.PrincipalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MarketServiceImplTest {

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private PrincipalRepository principalRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private MarketServiceImpl marketService;


    @Test
    public void testCreateMarket_Success() {
        MarketRequestDTO requestDTO = MarketRequestDTO.builder()
                .code("MKT1")
                .description("Mercado 1")
                .country("Argentina")
                .principals(Arrays.asList(1L, 2L))
                .build();

        Country country = Country.builder().name("Argentina").build();

        Principal principal1 = new Principal();
        principal1.setId(1L);

        Principal principal2 = new Principal();
        principal2.setId(2L);

        when(countryRepository.findByName("Argentina")).thenReturn(Optional.of(country));
        when(principalRepository.findById(1L)).thenReturn(Optional.of(principal1));
        when(principalRepository.findById(2L)).thenReturn(Optional.of(principal2));

        when(marketRepository.save(any(Market.class))).thenAnswer(invocation -> {
            Market market = invocation.getArgument(0);
            market.setId(1L);
            return market;
        });

        MarketDTO createdMarket = marketService.createMarket(requestDTO);

        assertNotNull(createdMarket);
        assertEquals("MKT1", createdMarket.getCode());
        assertEquals("Mercado 1", createdMarket.getDescription());
        assertEquals("Argentina", createdMarket.getCountry().getName());
        assertEquals(2, createdMarket.getPrincipals().size());
    }

    @Test
    public void testGetAllMarkets_Success() {
        Market market1 = Market.builder()
                .id(1L)
                .code("MKT1")
                .description("Mercado 1")
                .country(Country.builder().id(1L).name("Argentina").build())
                .principals(new HashSet<>())
                .build();

        Market market2 = Market.builder()
                .id(2L)
                .code("MKT2")
                .country(Country.builder().id(1L).name("Argentina").build())
                .principals(new HashSet<>())
                .description("Mercado 2")
                .build();

        when(marketRepository.findAll()).thenReturn(Arrays.asList(market1, market2));

        List<MarketDTO> marketDTOList = marketService.getAllMarkets();

        assertNotNull(marketDTOList);
        assertEquals(2, marketDTOList.size());
        assertEquals("MKT1", marketDTOList.get(0).getCode());
        assertEquals("MKT2", marketDTOList.get(1).getCode());
    }

    @Test
    public void testGetMarketById_Success() {
        Long marketId = 1L;
        Market market = Market.builder()
                .id(marketId)
                .code("MKT1")
                .description("Mercado 1")
                .country(Country.builder().id(1L).name("Argentina").build())
                .principals(new HashSet<>())
                .build();

        when(marketRepository.findById(marketId)).thenReturn(Optional.of(market));

        MarketDTO marketDTO = marketService.getMarketById(marketId);

        assertNotNull(marketDTO);
        assertEquals(marketId, marketDTO.getId());
        assertEquals("MKT1", marketDTO.getCode());
        assertEquals("Mercado 1", marketDTO.getDescription());
    }

    @Test
    public void testUpdateMarket_Success() {
        Long marketId = 1L;
        UpdateMarketRequestDTO updateRequest = UpdateMarketRequestDTO.builder()
                .code("MKT1_UPDATED")
                .description("Mercado 1 Actualizado")
                .country("Argentina")
                .build();

        Market existingMarket = Market.builder()
                .id(marketId)
                .code("MKT1")
                .description("Mercado 1")
                .principals(new HashSet<>())
                .build();

        Country country = Country.builder()
                .id(1L)
                .name("Argentina")
                .build();

        when(marketRepository.findById(marketId)).thenReturn(Optional.of(existingMarket));
        when(countryRepository.findByName("Argentina")).thenReturn(Optional.of(country));
        when(marketRepository.save(any(Market.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MarketDTO updatedMarketDTO = marketService.updateMarket(marketId, updateRequest);

        assertNotNull(updatedMarketDTO);
        assertEquals(marketId, updatedMarketDTO.getId());
        assertEquals("MKT1_UPDATED", updatedMarketDTO.getCode());
        assertEquals("Mercado 1 Actualizado", updatedMarketDTO.getDescription());
        assertEquals("Argentina", updatedMarketDTO.getCountry().getName());
    }

    @Test
    public void testDeleteMarket_Success() {
        Long marketId = 1L;
        Market existingMarket = Market.builder()
                .id(marketId)
                .code("MKT1")
                .description("Mercado 1")
                .country(Country.builder().id(1L).name("Argentina").build())
                .principals(new HashSet<>())
                .build();

        when(marketRepository.findById(marketId)).thenReturn(Optional.of(existingMarket));

        boolean deleted = marketService.deleteMarket(marketId);

        assertTrue(deleted);
        verify(marketRepository, times(1)).delete(existingMarket);
    }

}