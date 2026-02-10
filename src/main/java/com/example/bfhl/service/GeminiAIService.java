package com.example.bfhl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiAIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://generativelanguage.googleapis.com")
            .build();

    public String ask(String question) {

        // Force single-word response at the AI level
        String prompt = question +
                ". Answer in exactly ONE WORD only. No explanation.";

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        try {
            Map<?, ?> response = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1beta/models/gemini-2.5-flash:generateContent")
                            .queryParam("key", apiKey)
                            .build())
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null || !response.containsKey("candidates")) {
                return "Unknown";
            }

            List<?> candidates = (List<?>) response.get("candidates");
            if (candidates.isEmpty()) {
                return "Unknown";
            }

            Map<?, ?> content =
                    (Map<?, ?>) ((Map<?, ?>) candidates.get(0)).get("content");
            List<?> parts = (List<?>) content.get("parts");

            if (parts == null || parts.isEmpty()) {
                return "Unknown";
            }

            return ((Map<?, ?>) parts.get(0))
                    .get("text")
                    .toString()
                    .trim();

        } catch (Exception e) {
            System.out.println("Gemini AI error: " + e.getMessage());
            return "Unknown";
        }
    }
}
