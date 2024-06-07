package challenge.decrypto.models.countries;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CountryDTO {
    private Long id;
    private String name;
}
