package challenge.decrypto.repositories;

import challenge.decrypto.entities.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    Optional<Market> findById(Long id);
    Optional<Market> findByCode(String code);

}
