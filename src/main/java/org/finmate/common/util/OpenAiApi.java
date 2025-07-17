package org.finmate.common.util;

import org.finmate.common.util.OpenAiDTO.OpenAiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OpenAiApi {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.apikey}") String apiKey;
    @Value("${openai.url}") String apiUrl;

    public OpenAiResponseDTO callResponses(String input) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 요청 바디
        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "input", input
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<OpenAiResponseDTO> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                request,
                OpenAiResponseDTO.class
        );

        return response.getBody();
    }
}
