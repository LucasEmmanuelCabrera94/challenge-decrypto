package challenge.decrypto.utils.mappers;

import challenge.decrypto.entities.Market;
import challenge.decrypto.models.markets.MarketDTO;

public class MarketMapper {
    public static MarketDTO toDTO(Market market) {
        return MarketDTO.builder()
                .code(market.getCode())
                .build();
    }
}
