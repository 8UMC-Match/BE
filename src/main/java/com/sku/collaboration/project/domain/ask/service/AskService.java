package com.sku.collaboration.project.domain.ask.service;

import com.sku.collaboration.project.domain.ask.dto.request.AskRequest;
import com.sku.collaboration.project.domain.ask.dto.request.AskWordRequest;
import com.sku.collaboration.project.domain.ask.dto.response.AskResponse;
import com.sku.collaboration.project.domain.ask.dto.response.AskWordResponse;
import com.sku.collaboration.project.domain.ask.dto.response.AskWordsResponse;
import com.sku.collaboration.project.domain.ask.entity.Ask;
import com.sku.collaboration.project.domain.ask.repository.AskRepository;
import com.sku.collaboration.project.domain.askWord.entity.AskWord;
import com.sku.collaboration.project.domain.askWord.repository.AskWordRepository;
import com.sku.collaboration.project.domain.chat.dto.Message;
import com.sku.collaboration.project.domain.chat.dto.request.ChatRequest;
import com.sku.collaboration.project.domain.chat.dto.response.ChatResponse;
import com.sku.collaboration.project.domain.user.entity.User;
import com.sku.collaboration.project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AskService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private final AskRepository askRepository;
    private final UserRepository userRepository;
    private final AskWordRepository askWordRepository;

    public AskResponse toAskResponse(Long userId, AskRequest askRequest) {
        String prompt = askRequest.getQuestion() + " 이 문장을 지적 장애인 수준에서 이해하기 쉬운 문장으로 변환해서 내게 보내줘. 대답은 필요없어";

        List<Message> messages = List.of(
                new Message("system", "You are a helpful assistant."),
                new Message("user", prompt)
        );

        ChatRequest request = new ChatRequest(model, messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatResponse> response = restTemplate.postForEntity(apiUrl, entity, ChatResponse.class);

        String aiContent = response.getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();

        User user = userRepository.findById(userId)
                .orElseThrow();

        Ask ask = Ask.builder()
                .question(askRequest.getQuestion())
                .user(user)
                .build();

        Ask saved = askRepository.save(ask);

        return AskResponse.builder()
                .questionId(saved.getId())
                .content(aiContent)
                .build();
    }

    @Transactional
    public AskWordsResponse toAskWordsResponse(Long userId, Long askId) {
        // Ask 엔티티에서 question 추출
        Ask ask = askRepository.findById(askId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        String prompt = ask.getQuestion() +
                " 이 문장 중 지적 장애인 수준에서 이해하기 어려운 단어 5개를 선정해서 " +
                "1. 단어 : 설명\n2. 단어 : 설명 형식으로 보내줘. 대답은 필요없어";

        List<Message> messages = List.of(
                new Message("system", "You are a helpful assistant."),
                new Message("user", prompt)
        );

        ChatRequest request = new ChatRequest(model, messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<ChatResponse> response = restTemplate.postForEntity(apiUrl, entity, ChatResponse.class);

        String aiContent = response.getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();

        // AskWord 저장
        List<AskWordResponse> resultList = new ArrayList<>();

        String[] lines = aiContent.split("\\n");
        for (String line : lines) {
            String[] parts = line.split(":", 2);
            if (parts.length < 2) continue;

            String word = parts[0].replaceAll("^[0-9.\\s]+", "").trim();
            String description = parts[1].trim();

            AskWord askWord = AskWord.builder()
                    .name(word)
                    .description(description)
                    .ask(ask) // 기존 ask 사용
                    .build();

            AskWord saved = askWordRepository.save(askWord);

            resultList.add(
                    AskWordResponse.builder()
                            .askWordId(saved.getId())
                            .name(saved.getName())
                            .description(saved.getDescription())
                            .build()
            );
        }

        return AskWordsResponse.builder()
                .data(resultList)
                .build();
    }

    @Transactional
    public AskWordResponse toAskWordResponse(Long userId, Long askId, AskWordRequest askWordRequest) {
        // 1. Ask 조회
        Ask ask = askRepository.findById(askId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        // 2. 프롬프트 생성 (단일 단어에 대한 설명 요청)
        String prompt = askWordRequest.getName() + " 이 단어를 지적 장애인 수준에서 이해하기 쉬운 문장으로 설명해줘. 대답은 필요없어";

        List<Message> messages = List.of(
                new Message("system", "You are a helpful assistant."),
                new Message("user", prompt)
        );

        // 3. 요청 객체 구성
        ChatRequest request = new ChatRequest(model, messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<ChatResponse> response = restTemplate.postForEntity(apiUrl, entity, ChatResponse.class);

        // 4. GPT 응답 내용 추출
        String description = response.getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent()
                .trim();

        // 5. AskWord 저장
        AskWord askWord = AskWord.builder()
                .name(askWordRequest.getName())
                .description(description)
                .ask(ask)
                .build();

        AskWord saved = askWordRepository.save(askWord);

        // 6. 단일 DTO 반환
        return AskWordResponse.builder()
                .askWordId(saved.getId())
                .name(saved.getName())
                .description(saved.getDescription())
                .build();
    }
}
