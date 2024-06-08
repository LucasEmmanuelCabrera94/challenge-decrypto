package challenge.decrypto.services;

import challenge.decrypto.entities.Country;
import challenge.decrypto.models.countries.CountryDTO;
import challenge.decrypto.repositories.CountryRepository;
import challenge.decrypto.utils.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    @Test
    public void testCreateCountry_Successful() {
        CountryDTO request = CountryDTO.builder().name("Argentina").build();

        when(countryRepository.findByName("Argentina")).thenReturn(Optional.empty());
        when(countryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CountryDTO result = countryService.createCountry(request);

        assertNotNull(result);
        assertEquals("Argentina", result.getName());
        verify(countryRepository, times(1)).findByName("Argentina");
        verify(countryRepository, times(1)).save(any());
    }

    @Test
    public void testCreateCountry_DuplicateName() {
        CountryDTO request = CountryDTO.builder().name("Argentina").build();

        when(countryRepository.findByName("Argentina")).thenReturn(Optional.of(new Country()));

        assertThrows(BadRequestException.class, () -> countryService.createCountry(request));
        verify(countryRepository, times(1)).findByName("Argentina");
        verify(countryRepository, never()).save(any());
    }

    @Test
    public void testCreateCountry_InvalidName() {
        CountryDTO request = CountryDTO.builder().name("Brasil").build();

        assertThrows(BadRequestException.class, () -> countryService.createCountry(request));
        verify(countryRepository, never()).findByName(any());
        verify(countryRepository, never()).save(any());
    }

    @Test
    public void testGetAllCountries() {
        List<Country> mockCountries = Arrays.asList(
                new Country(1L, "Argentina"),
                new Country(2L, "Uruguay")
        );

        when(countryRepository.findAll()).thenReturn(mockCountries);

        List<CountryDTO> result = countryService.getAllCountries();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Argentina", result.get(0).getName());
        assertEquals("Uruguay", result.get(1).getName());
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    public void testGetCountryById_Successful() {
        Long countryId = 1L;
        Country mockCountry = new Country(countryId, "Argentina");

        when(countryRepository.findById(countryId)).thenReturn(Optional.of(mockCountry));

        CountryDTO result = countryService.getCountryById(countryId);

        assertNotNull(result);
        assertEquals("Argentina", result.getName());
        verify(countryRepository, times(1)).findById(countryId);
    }

    @Test
    public void testGetCountryById_NotFound() {
        Long countryId = 1L;

        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> countryService.getCountryById(countryId));
        verify(countryRepository, times(1)).findById(countryId);
    }
}