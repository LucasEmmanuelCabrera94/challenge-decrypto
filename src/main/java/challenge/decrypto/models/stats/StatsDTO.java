package challenge.decrypto.models.stats;

import challenge.decrypto.models.markets.MarketStatsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDTO {
    private String country;
    private List<MarketStatsDTO> markets;
}
