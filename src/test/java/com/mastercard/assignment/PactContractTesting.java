package com.mastercard.assignment;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PactContractTesting {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("test_provider", "localhost", 8080, this);

    @Pact(consumer = "test_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test GET")
                .uponReceiving("GET REQUEST")
                .path("/search/chris").method("GET")
                .willRespondWith().status(200)
                .headers(headers)
                .body("{\"searchTerm\":\"chris\",\"movies\":[\"Movie2\",\"Movie2\"],\"count\":2}")
                .toPact();
    }

    @Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {

        // when
        ResponseEntity<String> response = new RestTemplate()
                .getForEntity(mockProvider.getUrl() + "/search/chris", String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody()).contains("{\"searchTerm\":\"chris\",\"movies\":[\"Movie2\",\"Movie2\"],\"count\":2}");
    }

}
