package challenge.decrypto.models.markets;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MarketRequestDTO {
    private String code;
    private String description;
    private String country;
    private List<Long> principals;
}
