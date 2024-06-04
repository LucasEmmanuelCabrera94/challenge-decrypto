package challenge.decrypto.models.percentages;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PercentageDTO {
    private String percentage; //TODO: usar DecimalFormat("0.00")
}
