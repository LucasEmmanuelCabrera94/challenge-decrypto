package challenge.decrypto.utils.mappers;

import challenge.decrypto.entities.Market;
import challenge.decrypto.models.markets.MarketDTO;

import java.util.stream.Collectors;

public class MarketMapper {

    public static MarketDTO toDTO(Market market) {
        return MarketDTO.builder()
                .id(market.getId())
                .code(market.getCode())
                .description(market.getDescription())
                .country(CountryMapper.toDTO(market.getCountry()))
                .principals(market.getPrincipals().stream()
                        .map(PrincipalMapper::toMarketDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public static MarketDTO toPrincipalDTO(Market market) {
        return MarketDTO.builder()
                .code(market.getCode())
                .build();
    }
}
