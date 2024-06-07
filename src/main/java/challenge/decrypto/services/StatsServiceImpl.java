package challenge.decrypto.services;

import challenge.decrypto.entities.Country;
import challenge.decrypto.entities.Market;
import challenge.decrypto.models.markets.MarketInfoDTO;
import challenge.decrypto.models.stats.StatsDTO;
import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.services.interfaces.StatsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static challenge.decrypto.utils.mappers.StatsMapper.convertToStatsDTO;

public class StatsServiceImpl implements StatsService {
    private MarketRepository marketRepository;

    public StatsServiceImpl(MarketRepository marketRepository){
        this.marketRepository = marketRepository;
    }

    @Override
    public List<StatsDTO> getStats() {
        List<Market> markets = marketRepository.findAll();
        Map<String, List<MarketInfoDTO>> marketInfoMap = new HashMap<>();

        Map<Country, List<Market>> marketsByCountry = markets.stream()
                .collect(Collectors.groupingBy(Market::getCountry));

        for (Map.Entry<Country, List<Market>> entry : marketsByCountry.entrySet()) {
            String countryName = entry.getKey().getName();
            List<MarketInfoDTO> marketInfoList = new ArrayList<>();

            for (Market market : entry.getValue()) {
                int PrincipalsQuantity = market.getPrincipals().size();
                MarketInfoDTO marketInfoDTO = MarketInfoDTO.builder()
                        .marketCode(market.getCode())
                        .country(countryName)
                        .principalsQuantity(PrincipalsQuantity)
                        .build();

                marketInfoList.add(marketInfoDTO);
            }

            marketInfoMap.put(countryName, marketInfoList);
        }

        return convertToStatsDTO(marketInfoMap);
    }
}
