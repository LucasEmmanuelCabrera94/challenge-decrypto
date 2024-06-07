package challenge.decrypto.models.markets;

import challenge.decrypto.entities.Country;
import challenge.decrypto.models.principals.PrincipalDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
public class MarketDTO {
    private long id;
    private String code;
    private String description;
    private Country country;
    private List<PrincipalDTO> principals;
}
