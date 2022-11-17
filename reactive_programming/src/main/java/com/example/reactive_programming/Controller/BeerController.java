package com.example.reactive_programming.Controller;

import com.example.reactive_programming.Model.Beer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.time.Duration;

@RestController
public class BeerController {

    @RequestMapping(path = "/getBeerFromAPI/{minPrice}/{maxPrice}/{minRating}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Beer> beerFlux(@PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String minRating) {
        Double minPriceDouble = (minPrice.equals("null")) ? 0.0 : Double.parseDouble(minPrice);
        Double maxPriceDouble = (maxPrice.equals("null")) ? Double.MAX_VALUE : Double.parseDouble(maxPrice);
        Double minRatingDouble = (minRating.equals("null")) ? 0.0 : Double.parseDouble(minRating);

        return WebClient.create("https://api.sampleapis.com/beers/ale")
                .get()
                .retrieve()
                .bodyToFlux(Beer.class)
                .map(beer -> {
                    beer.setPrice("€ " + convertToEuro(beer.getPrice()));
                    return beer;
                })
                .filterWhen(beer -> Mono.justOrEmpty(convertToEuro(beer.getPrice()) > minPriceDouble))
                .filterWhen(beer -> Mono.justOrEmpty(convertToEuro(beer.getPrice()) < maxPriceDouble))
                .filterWhen(beer -> Mono.justOrEmpty(beer.getRating().getAverage() > minRatingDouble))
                .delayElements(Duration.ofSeconds(5));
    }


    private Double convertToEuro(String dollars) {
        DecimalFormat df = new DecimalFormat("####0.00");
        Double euros = 0.0;
        dollars = dollars.substring(1, dollars.length());
        euros = Double.parseDouble(dollars) / 1.04;
        return Double.valueOf(df.format(euros));
    }
}
