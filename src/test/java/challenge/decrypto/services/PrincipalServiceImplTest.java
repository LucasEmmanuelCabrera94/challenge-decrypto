package challenge.decrypto.services;

import challenge.decrypto.entities.Market;
import challenge.decrypto.entities.Principal;
import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequestDTO;
import challenge.decrypto.models.principals.UpdatePrincipalRequestDTO;
import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.repositories.PrincipalRepository;
import challenge.decrypto.utils.exceptions.BadRequestException;
import challenge.decrypto.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrincipalServiceImplTest {
    @Mock
    private PrincipalRepository principalRepository;

    @Mock
    private MarketRepository marketRepository;

    @InjectMocks
    private PrincipalServiceImpl principalService;

    @Test
    public void testCreatePrincipal() {
        PrincipalRequestDTO requestDTO = PrincipalRequestDTO.builder()
                .description("Nuevo comitente")
                .markets(List.of(1L, 2L))
                .build();

        Market market1 = Market.builder().id(1L).build();
        Market market2 = Market.builder().id(2L).build();

        Set<Market> markets = Set.of(market1, market2);

        when(marketRepository.findById(1L)).thenReturn(Optional.of(market1));
        when(marketRepository.findById(2L)).thenReturn(Optional.of(market2));
        when(principalRepository.save(any(Principal.class))).thenAnswer(invocation -> {
            Principal principal = invocation.getArgument(0);
            principal.setId(1L);
            return principal;
        });

        PrincipalDTO createdPrincipalDTO = principalService.createPrincipal(requestDTO);

        assertNotNull(createdPrincipalDTO);
        assertEquals("Nuevo comitente", createdPrincipalDTO.getDescription());
        assertEquals(2, createdPrincipalDTO.getMarkets().size());
        verify(principalRepository, times(1)).save(any(Principal.class));
    }

    @Test
    public void testGetAllPrincipals() {
        Principal principal1 = new Principal();
        principal1.setId(1L);
        principal1.setMarkets(new HashSet<>());
        principal1.setDescription("Comitente 1");
        Principal principal2 = new Principal();
        principal2.setId(2L);
        principal2.setMarkets(new HashSet<>());
        principal2.setDescription("Comitente 2");
        List<Principal> principalList = List.of(principal1, principal2);

        when(principalRepository.findAllWithMarkets()).thenReturn(principalList);

        List<PrincipalDTO> allPrincipalsDTO = principalService.getAllPrincipals();

        assertNotNull(allPrincipalsDTO);
        assertEquals(2, allPrincipalsDTO.size());
        assertEquals("Comitente 1", allPrincipalsDTO.get(0).getDescription());
        assertEquals("Comitente 2", allPrincipalsDTO.get(1).getDescription());
        verify(principalRepository, times(1)).findAllWithMarkets();
    }

    @Test
    public void testGetPrincipalById_Success() {
        Long principalId = 1L;
        Principal principal = new Principal();
        principal.setId(principalId);
        principal.setMarkets(new HashSet<>());
        principal.setDescription("Comitente 1");

        when(principalRepository.findById(principalId)).thenReturn(Optional.of(principal));
        PrincipalDTO result = principalService.getPrincipalById(principalId);

        assertNotNull(result);
        assertEquals(principalId, result.getId());
        assertEquals("Comitente 1", result.getDescription());
        verify(principalRepository, times(1)).findById(principalId);
    }

    @Test
    public void testGetPrincipalById_NotFound() {
        Long principalId = 1L;

        when(principalRepository.findById(principalId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            principalService.getPrincipalById(principalId);
        });

        verify(principalRepository, times(1)).findById(principalId);
    }

    @Test
    public void testUpdatePrincipal_Success() {
        Long principalId = 1L;
        String newDescription = "Nuevo Comitente";
        UpdatePrincipalRequestDTO requestDTO = UpdatePrincipalRequestDTO.builder().description(newDescription).build();

        Principal principal = new Principal();
        principal.setId(principalId);
        principal.setMarkets(new HashSet<>());
        principal.setDescription("Comitente Antiguo");

        when(principalRepository.findById(principalId)).thenReturn(Optional.of(principal));
        when(principalRepository.save(any(Principal.class))).thenAnswer(invocation -> invocation.getArgument(0));
        PrincipalDTO result = principalService.updatePrincipal(principalId, requestDTO);

        assertNotNull(result);
        assertEquals(principalId, result.getId());
        assertEquals(newDescription, result.getDescription());
        verify(principalRepository, times(1)).findById(principalId);
        verify(principalRepository, times(1)).save(principal);
    }

    @Test
    public void testUpdatePrincipal_NotFound() {
        Long principalId = 1L;
        UpdatePrincipalRequestDTO requestDTO = new UpdatePrincipalRequestDTO();
        requestDTO.setDescription("Nuevo Comitente");
        when(principalRepository.findById(principalId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            principalService.updatePrincipal(principalId, requestDTO);
        });

        verify(principalRepository, times(1)).findById(principalId);
    }

    @Test
    public void testUpdatePrincipal_DescriptionNotProvided() {
        Long principalId = 1L;
        UpdatePrincipalRequestDTO requestDTO = new UpdatePrincipalRequestDTO();
        requestDTO.setDescription(null);

        Principal principal = new Principal();
        principal.setId(principalId);
        principal.setDescription("Comitente Antiguo");

        when(principalRepository.findById(principalId)).thenReturn(Optional.of(principal));
        assertThrows(BadRequestException.class, () -> {
            principalService.updatePrincipal(principalId, requestDTO);
        });

        verify(principalRepository, times(1)).findById(principalId);
        verify(principalRepository, times(0)).save(principal);
    }

    @Test
    public void testDeletePrincipal_Success() {
        Long principalId = 1L;

        when(principalRepository.existsById(principalId)).thenReturn(true);
        boolean result = principalService.deletePrincipal(principalId);

        Assertions.assertTrue(result);
        verify(principalRepository, times(1)).existsById(principalId);
        verify(principalRepository, times(1)).deleteById(principalId);
    }

    @Test
    public void testDeletePrincipal_NotFound() {
        Long principalId = 1L;

        when(principalRepository.existsById(principalId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            principalService.deletePrincipal(principalId);
        });

        verify(principalRepository, times(1)).existsById(principalId);
        verify(principalRepository, times(0)).deleteById(principalId);
    }

    @Test
    public void testCreatePrincipal_DescriptionExists() {
        String description = "Comitente Existente";
        PrincipalRequestDTO requestDTO = PrincipalRequestDTO.builder().description(description).build();

        when(principalRepository.existsByDescription(description)).thenReturn(true);
        assertThrows(BadRequestException.class, () -> {
            principalService.createPrincipal(requestDTO);
        });

        verify(principalRepository, times(1)).existsByDescription(description);
        verify(principalRepository, times(0)).save(any(Principal.class));
    }

    @Test
    public void testCreatePrincipal_NoMarketsProvided() {
        String description = "Comitente Sin Mercados";
        PrincipalRequestDTO requestDTO = PrincipalRequestDTO.builder().description(description).markets(Collections.emptyList()).build();

        when(principalRepository.existsByDescription(description)).thenReturn(false);
        assertThrows(BadRequestException.class, () -> {
            principalService.createPrincipal(requestDTO);
        });

        verify(principalRepository, times(1)).existsByDescription(description);
        verify(principalRepository, times(0)).save(any(Principal.class));
    }

}
