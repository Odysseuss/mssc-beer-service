package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.sfg.brewery.model.events.BrewBeerEvent;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.sfg.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
        BeerDto beerDto = event.getBeerDto();

        Optional<Beer> optionalBeer = beerRepository.findById(beerDto.getId());
        optionalBeer.ifPresentOrElse(
                beer -> {
                    beerDto.setQuantityOnHand(beer.getQuantityToBrew());
                    NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
                    log.debug("Brewed " + beerDto.getQuantityOnHand() + " " + beerDto.getBeerName());
                    jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
                },
                () -> {
                    log.info("Received request for beer not found. ID: " + beerDto.getId());
                });
    }
}