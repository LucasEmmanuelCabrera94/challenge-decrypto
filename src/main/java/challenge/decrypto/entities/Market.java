package challenge.decrypto.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Entity
@Builder
@Getter
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToMany
    @JoinTable(
            name = "principal_market",
            joinColumns = @JoinColumn(name = "market_id"),
            inverseJoinColumns = @JoinColumn(name = "principal_id")
    )
    private Set<Principal> principals;
}
