package com.sku.collaboration.project.domain.quiz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sku.collaboration.project.domain.quiz.dto.response.QuizPromptResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GPTQuizUtil {

  @Value("${OPEN_API_KEY}")
  private String apiKey;

  private final ObjectMapper objectMapper;

  public List<QuizPromptResponse> generateOXQuiz(String questionText) throws JsonProcessingException{
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    String prompt = """
너는 JSON 전문가야. 아래 문장을 기반으로 어린이가 풀 수 있는 O/X 퀴즈 5개를 만들어.
반드시 설명 없이 정확하게 JSON 배열 형식만 반환해.
예시:
[
  {"content": "하늘은 파랗다.", "correct": 1},
  {"content": "고양이는 식물이다.", "correct": 0}
]
문장: %s
""".formatted(questionText);

    String requestBody = "";
    try {
      requestBody = """
        {
          "model": "gpt-4",
          "messages": [
            {"role": "user", "content": %s}
          ],
          "temperature": 0.7
        }
        """.formatted(objectMapper.writeValueAsString(prompt));
    } catch (JsonProcessingException e) {
      e.printStackTrace(); // 혹은 로그 출력, 예외 전파 등
    }

    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
    ResponseEntity<String> response = restTemplate.exchange(
        URI.create("https://api.openai.com/v1/chat/completions"),
        HttpMethod.POST,
        request,
        String.class
    );

    JsonNode choices = objectMapper.readTree(response.getBody()).get("choices");
    String quizJson = choices.get(0).get("message").get("content").asText().trim();
    if (!quizJson.startsWith("[")) {
      int index = quizJson.indexOf("[");
      quizJson = quizJson.substring(index); // 앞 설명 제거
    }

    // 퀴즈 JSON 배열 파싱
    JsonNode quizArray = objectMapper.readTree(quizJson);
    System.out.println("GPT 응답 원문: " + response.getBody());
    System.out.println("GPT 메시지 content: " + quizJson);
    List<QuizPromptResponse> quizList = new ArrayList<>();
    for (JsonNode node : quizArray) {
      quizList.add(objectMapper.treeToValue(node, QuizPromptResponse.class));
    }

    return quizList;
  }
}
