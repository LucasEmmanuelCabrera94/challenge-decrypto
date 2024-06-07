package challenge.decrypto.configurations;

import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.repositories.PrincipalRepository;
import challenge.decrypto.services.PrincipalServiceImpl;
import challenge.decrypto.services.StatsServiceImpl;
import challenge.decrypto.services.interfaces.PrincipalService;
import challenge.decrypto.services.interfaces.StatsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public PrincipalService principalService(PrincipalRepository principalRepository, MarketRepository marketRepository){
        return new PrincipalServiceImpl(principalRepository, marketRepository);
    }

    @Bean
    public StatsService statsService(MarketRepository marketRepository){
        return new StatsServiceImpl(marketRepository);
    }
}
