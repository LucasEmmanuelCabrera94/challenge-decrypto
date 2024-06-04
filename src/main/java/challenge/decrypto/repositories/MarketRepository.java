package challenge.decrypto.repositories;

import challenge.decrypto.entities.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
