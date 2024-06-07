package challenge.decrypto.services.interfaces;


import challenge.decrypto.models.markets.MarketDTO;
import challenge.decrypto.models.markets.MarketRequestDTO;
import challenge.decrypto.models.markets.UpdateMarketRequestDTO;

import java.util.List;

public interface MarketService {
   MarketDTO createMarket(MarketRequestDTO request);

    List<MarketDTO> getAllMarkets();
    MarketDTO getMarketById(Long id);
    MarketDTO updateMarket(Long id, UpdateMarketRequestDTO request);
    boolean deleteMarket(Long id);
}
