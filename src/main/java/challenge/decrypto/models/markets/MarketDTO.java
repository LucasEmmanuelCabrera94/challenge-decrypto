package challenge.decrypto.models.markets;

import challenge.decrypto.models.percentages.PercentageDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MarketDTO {
    private String code;
    private PercentageDTO percentage;
}
