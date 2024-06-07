package challenge.decrypto.services.interfaces;

import challenge.decrypto.models.stats.StatsDTO;

import java.util.List;

public interface StatsService {
    List<StatsDTO> getStats();
}
