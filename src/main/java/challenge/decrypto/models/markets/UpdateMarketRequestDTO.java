package challenge.decrypto.models.markets;

import challenge.decrypto.entities.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMarketRequestDTO {
    private String description;
    private String code;
    private String country;
}
