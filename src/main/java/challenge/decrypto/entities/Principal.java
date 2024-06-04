package challenge.decrypto.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Entity
@Builder
@Getter
public class Principal { //Comitentes (en internet dicen que es la traduccion)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String description;

    @ManyToMany(mappedBy = "principals")
    private Set<Market> markets;

}
