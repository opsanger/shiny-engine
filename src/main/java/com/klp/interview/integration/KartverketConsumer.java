package com.klp.interview.integration;

import com.klp.interview.model.FylkesnavnResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class KartverketConsumer {

    private final WebClient webClient;
    private static final String BASE_URL = "https://api.kartverket.no/kommuneinfo/v1/fylker";

    public KartverketConsumer(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public FylkesnavnResponse findCountyName(String countyNumber) {
        URI uri = fromHttpUrl(BASE_URL)
                .pathSegment(countyNumber)
                .queryParam("filtrer", "fylkesnavn")
                .build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(FylkesnavnResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new CountyNotFoundException("Ugyldig fylkesnummer " + countyNumber));
                    }
                    return Mono.error(new RuntimeException("Kartverket API feilet", ex));
                })
                .block();
    }
}
