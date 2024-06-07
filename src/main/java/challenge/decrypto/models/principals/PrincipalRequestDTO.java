package challenge.decrypto.models.principals;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PrincipalRequestDTO {
    private String description;
    private List<Long> markets;
}
