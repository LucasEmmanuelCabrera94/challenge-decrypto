package challenge.decrypto.configurations;

import challenge.decrypto.entities.Country;
import challenge.decrypto.entities.Market;
import challenge.decrypto.repositories.CountryRepository;
import challenge.decrypto.repositories.MarketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(CountryRepository countryRepository, MarketRepository marketRepository) {
        return args -> {
            Country argentina = Country.builder()
                    .name("Argentina")
                    .build();

            Country uruguay = Country.builder()
                    .name("Uruguay")
                    .build();

            countryRepository.save(argentina);
            countryRepository.save(uruguay);

            Market market1 = Market.builder()
                    .code("MKT1")
                    .description("Market 1")
                    .country(argentina)
                    .build();

            Market market2 = Market.builder()
                    .code("MKT2")
                    .description("Market 2")
                    .country(uruguay)
                    .build();

            marketRepository.save(market1);
            marketRepository.save(market2);
        };
    }
}

