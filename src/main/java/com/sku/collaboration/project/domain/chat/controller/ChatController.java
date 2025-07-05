package com.sku.collaboration.project.domain.chat.controller;

import com.sku.collaboration.project.domain.chat.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiService openAiService;

    @PostMapping("/chat")
    public String ask(@RequestBody String prompt) {
        return openAiService.getChatResponse(prompt);
    }
}
