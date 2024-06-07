package challenge.decrypto.models.markets;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarketInfoDTO {
    private String marketCode;
    private String country;
    private long principalsQuantity;
}