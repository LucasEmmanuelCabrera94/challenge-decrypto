package challenge.decrypto.utils.mappers;

import challenge.decrypto.models.markets.MarketInfoDTO;
import challenge.decrypto.models.markets.MarketStatsDTO;
import challenge.decrypto.models.stats.StatsDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsMapper {

    public static List<StatsDTO> convertToStatsDTO(Map<String, List<MarketInfoDTO>> marketInfoMap) {
        return marketInfoMap.entrySet().stream()
                .map(entry -> {
                    String country = entry.getKey();
                    List<MarketInfoDTO> marketInfoList = entry.getValue();
                    List<MarketStatsDTO> marketStatsDTOList = marketInfoList.stream()
                            .map(StatsMapper::convertToMarketStatsDTO)
                            .collect(Collectors.toList());

                    return StatsDTO.builder()
                            .country(country)
                            .markets(marketStatsDTOList)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private static MarketStatsDTO convertToMarketStatsDTO(MarketInfoDTO marketInfoDTO) {
        return MarketStatsDTO.builder()
                .marketCode(marketInfoDTO.getMarketCode())
                .principalQuantity(String.valueOf(marketInfoDTO.getPrincipalsQuantity()))
                .build();
    }
}

