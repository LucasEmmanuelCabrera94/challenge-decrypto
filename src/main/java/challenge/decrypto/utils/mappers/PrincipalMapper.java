package challenge.decrypto.utils.mappers;

import challenge.decrypto.entities.Principal;
import challenge.decrypto.models.principals.PrincipalDTO;

import java.util.stream.Collectors;

public class PrincipalMapper {
    public static PrincipalDTO toDTO(Principal principal) {
        return PrincipalDTO.builder()
                .id(principal.getId())
                .description(principal.getDescription())
                .markets(principal.getMarkets().stream()
                        .map(MarketMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
