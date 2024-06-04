package challenge.decrypto.repositories;

import challenge.decrypto.entities.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, Long> {
    boolean existsByDescription(String description);
}
