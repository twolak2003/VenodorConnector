package com.example.vendor.webflux;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpServiceClientTest {
    @Test
    void testHttpClientInstance() {
        HttpServiceClient client = new HttpServiceClient();
        assertNotNull(client);
    }
}