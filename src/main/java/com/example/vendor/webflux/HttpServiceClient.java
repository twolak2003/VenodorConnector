package com.example.vendor.webflux;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;

public class HttpServiceClient {
    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    public Mono<String> sendRequest(String payload) {
        return webClient.post()
                .uri("/process")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(2));
    }
}