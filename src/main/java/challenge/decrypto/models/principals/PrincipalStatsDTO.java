package challenge.decrypto.models.principals;

import challenge.decrypto.models.markets.MarketDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PrincipalStatsDTO {
    private String country;
    private List<MarketDTO> markets;
}
