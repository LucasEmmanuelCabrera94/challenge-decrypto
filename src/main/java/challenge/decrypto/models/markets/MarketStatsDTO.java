package challenge.decrypto.models.markets;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MarketStatsDTO {
    private String marketCode;
    private String principalQuantity;
}
