package com.sub.subscription.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class CrmValidationService {

    private final WebClient webClient;

    public CrmValidationService(@Value("${crm.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public boolean clientExists(Long clientId) {
        try {
            webClient.get()
                    .uri("/clients/{id}", clientId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }

    public boolean productExists(Long productId) {
        try {
            webClient.get()
                    .uri("/products/{id}", productId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }
}
