package com.example.vendor.mock;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class MockProcessingController {

    @PostMapping("/process")
    public Mono<String> process(@RequestBody String body) {
        System.out.println("Mock server received: " + body);
        return Mono.just("OK:" + body);
    }
}