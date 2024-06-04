package challenge.decrypto.configurations;

import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.repositories.PrincipalRepository;
import challenge.decrypto.services.PrincipalServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public PrincipalServiceImpl principalService(PrincipalRepository principalRepository, MarketRepository marketRepository){
        return new PrincipalServiceImpl(principalRepository, marketRepository);
    }
}
