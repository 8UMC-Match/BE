package com.sku.collaboration.project.domain.quiz.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sku.collaboration.project.domain.ask.entity.Ask;
import com.sku.collaboration.project.domain.ask.repository.AskRepository;
import com.sku.collaboration.project.domain.quiz.dto.response.QuizCreateResponse;
import com.sku.collaboration.project.domain.quiz.dto.response.QuizPromptResponse;
import com.sku.collaboration.project.domain.quiz.entity.Quiz;
import com.sku.collaboration.project.domain.quiz.repository.QuizRepository;
import com.sku.collaboration.project.domain.quiz.util.GPTQuizUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizService {

  private final AskRepository askRepository;
  private final QuizRepository quizRepository;
  private final GPTQuizUtil gptQuizUtil;  // GPT API 요청을 담당하는 컴포넌트


  public List<QuizCreateResponse> createQuizzes(Long askId) throws JsonProcessingException {
    Ask ask = askRepository.findById(askId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문 ID"));

    List<QuizPromptResponse> gptQuizzes = new ArrayList<>();
    try {
      gptQuizzes = gptQuizUtil.generateOXQuiz(ask.getQuestion());
    } catch (JsonProcessingException e) {
      log.error("퀴즈 생성 중 JSON 처리 오류", e);
      throw new RuntimeException("퀴즈 생성 실패");
    }

    List<QuizCreateResponse> responses = new ArrayList<>();
    int number = 1;

    for (QuizPromptResponse q : gptQuizzes) {
      Quiz quiz = Quiz.builder()
          .content(q.getContent())
          .correct(q.getCorrect())
          .ask(ask)
          .build();

      quizRepository.save(quiz);

      responses.add(new QuizCreateResponse(
          quiz.getId(), q.getContent(), number++, gptQuizzes.size()
      ));
    }

    return responses;
  }


//  @Transactional
//  public QuizStartResponse generateQuizByAskId(Long askId) {
//    Ask ask = askRepository.findById(askId)
//        .orElseThrow(() -> new CustomException(AskErrorCode.ASK_NOT_FOUND));
//
//    // GPT-4에 질문 내용을 넘겨 퀴즈 생성 요청
//    List<QuizCreateDto> quizList = gptClient.generateQuizList(ask.getQuestion());
//
//    // 퀴즈 저장
//    List<Quiz> savedQuizzes = quizList.stream().map(dto ->
//        quizRepository.save(Quiz.builder()
//            .content(dto.getContent())
//            .correct(dto.getCorrect())  // 0 또는 1
//            .ask(ask)
//            .build())
//    ).toList();
//
//    // 첫 번째 퀴즈만 응답에 포함
//    Quiz firstQuiz = savedQuizzes.get(0);
//
//    return QuizStartResponse.builder()
//        .quizId(firstQuiz.getId())
//        .content(firstQuiz.getContent())
//        .questionNumber(1)
//        .totalQuiz(5)
//        .build();
//  }

}
