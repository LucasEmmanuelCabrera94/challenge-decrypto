package challenge.decrypto.configurations;

import challenge.decrypto.entities.Country;
import challenge.decrypto.entities.Market;
import challenge.decrypto.entities.Principal;
import challenge.decrypto.repositories.CountryRepository;
import challenge.decrypto.repositories.MarketRepository;
import challenge.decrypto.repositories.PrincipalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(
            CountryRepository countryRepository,
            MarketRepository marketRepository,
            PrincipalRepository principalRepository) {
        return args -> {
            Country argentina = Country.builder().name("Argentina").build();
            Country uruguay = Country.builder().name("Uruguay").build();

            countryRepository.save(argentina);
            countryRepository.save(uruguay);

            Market market1 = Market.builder().code("MAE").description("Mercado 1").country(argentina).principals(new HashSet<>()).build();
            Market market2 = Market.builder().code("ROFEX").description("Mercado 2").country(argentina).principals(new HashSet<>()).build();
            Market market3 = Market.builder().code("UFEX").description("Mercado 3").country(uruguay).principals(new HashSet<>()).build();

            marketRepository.save(market1);
            marketRepository.save(market2);
            marketRepository.save(market3);

            Principal principal1 = Principal.builder().description("Comitente 1").markets(new HashSet<>()).build();
            Principal principal2 = Principal.builder().description("Comitente 2").markets(new HashSet<>()).build();
            Principal principal3 = Principal.builder().description("Comitente 3").markets(new HashSet<>()).build();

            principal1.getMarkets().add(market1);
            principal1.getMarkets().add(market2);

            principal2.getMarkets().add(market1);

            principal3.getMarkets().add(market3);

            principalRepository.save(principal1);
            principalRepository.save(principal2);
            principalRepository.save(principal3);
        };
    }

}

