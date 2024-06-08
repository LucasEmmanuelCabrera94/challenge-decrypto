package challenge.decrypto.services;

import challenge.decrypto.entities.Country;
import challenge.decrypto.models.countries.CountryDTO;
import challenge.decrypto.repositories.CountryRepository;
import challenge.decrypto.services.interfaces.CountryService;
import challenge.decrypto.utils.exceptions.BadRequestException;
import challenge.decrypto.utils.mappers.CountryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryDTO createCountry(CountryDTO request) {
        validateRequest(request);

        Optional<Country> existingCountry = countryRepository.findByName(request.getName());
        if (existingCountry.isPresent()) {
            throw new BadRequestException("Ya existe un país con el nombre proporcionado");
        }

        Country country = Country.builder()
                .name(request.getName())
                .build();

        Country response = countryRepository.save(country);
        return CountryMapper.toDTO(response);
    }

    @Override
    public List<CountryDTO> getAllCountries() {
        List<Country> countryList = countryRepository.findAll();
        return countryList.stream()
                .map(CountryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDTO getCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Pais no encontrado"));
        return CountryMapper.toDTO(country);}

    private static void validateRequest(CountryDTO request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new BadRequestException("El nombre del pais es requerido");
        }
        if (!"Argentina".equals(request.getName()) && !"Uruguay".equals(request.getName())) {
            throw new BadRequestException("Solo se pueden crear países con nombre Argentina o Uruguay");
        }
    }
}
