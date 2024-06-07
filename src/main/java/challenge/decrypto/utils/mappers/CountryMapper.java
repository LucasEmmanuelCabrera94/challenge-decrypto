package challenge.decrypto.utils.mappers;

import challenge.decrypto.entities.Country;
import challenge.decrypto.models.countries.CountryDTO;

public class CountryMapper {
    public static CountryDTO toDTO(Country country) {
        return CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

}
