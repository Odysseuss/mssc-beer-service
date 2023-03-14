package guru.springframework.msscbeerservice.bootstrap;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by jt on 2019-05-17.
 */
@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {
    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.count() == 0) {
            loadBeerObjects();
        }
    }

    private void loadBeerObjects() {
        Beer savedBeer = null;
        savedBeer = beerRepository.save(Beer.builder()
                .beerName("Mango Bobs")
                .beerStyle("IPA")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .minOnHand(12)
                .quantityToBrew(200)
                .price(BigDecimal.valueOf(12.95))
                .upc("0631234200036")
                .version(1L)
                .build());

        savedBeer = beerRepository.save(Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle("PALE_ALE")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .minOnHand(12)
                .quantityToBrew(200)
                .price(BigDecimal.valueOf(12.95))
                .upc("0631234300019")
                .version(1L)
                .build());

        savedBeer = beerRepository.save(Beer.builder()
                .beerName("Pinball Porter")
                .beerStyle("PORTER")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .minOnHand(12)
                .quantityToBrew(200)
                .price(BigDecimal.valueOf(12.95))
                .upc("0083783375213")
                .version(1L)
                .build());
    }
}
