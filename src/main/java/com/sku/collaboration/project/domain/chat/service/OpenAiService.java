package com.sku.collaboration.project.domain.chat.service;

import com.sku.collaboration.project.domain.ask.dto.request.AskRequest;
import com.sku.collaboration.project.domain.chat.dto.Message;
import com.sku.collaboration.project.domain.chat.dto.request.ChatRequest;
import com.sku.collaboration.project.domain.chat.dto.response.ChatResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getChatResponse(String userPrompt) {
        List<Message> messages = List.of(
                new Message("system", "You are a helpful assistant."),
                new Message("user", userPrompt)
        );

        ChatRequest request = new ChatRequest(model, messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatResponse> response = restTemplate.postForEntity(
                apiUrl, entity, ChatResponse.class
        );

        return response.getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }
}