package challenge.decrypto.models.markets;

import challenge.decrypto.models.countries.CountryDTO;
import challenge.decrypto.models.principals.PrincipalDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MarketDTO {
    private long id;
    private String code;
    private String description;
    private CountryDTO country;
    private List<PrincipalDTO> principals;
}
