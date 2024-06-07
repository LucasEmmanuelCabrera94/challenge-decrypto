package challenge.decrypto.repositories;

import challenge.decrypto.entities.Principal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, Long> {
    boolean existsByDescription(String description);
    @Query("SELECT p FROM Principal p LEFT JOIN FETCH p.markets")
    List<Principal> findAllWithMarkets();
}
