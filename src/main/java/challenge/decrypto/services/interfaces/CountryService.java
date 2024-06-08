package challenge.decrypto.services.interfaces;


import challenge.decrypto.models.countries.CountryDTO;

import java.util.List;

public interface CountryService {
    CountryDTO createCountry(CountryDTO request);

    List<CountryDTO> getAllCountries();
    CountryDTO getCountryById(Long id);
}
