package com.sku.collaboration.project.domain.quiz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sku.collaboration.project.domain.quiz.dto.response.QuizCreateResponse;
import com.sku.collaboration.project.domain.quiz.dto.response.QuizResultDetail;
import com.sku.collaboration.project.domain.quiz.dto.response.QuizResultResponse;
import com.sku.collaboration.project.domain.quiz.dto.response.QuizSolveResponse;
import com.sku.collaboration.project.domain.quiz.entity.Quiz;
import com.sku.collaboration.project.domain.quiz.exception.QuizErrorCode;
import com.sku.collaboration.project.domain.quiz.repository.QuizRepository;
import com.sku.collaboration.project.domain.quiz.service.QuizService;
import com.sku.collaboration.project.global.exception.CustomException;
import com.sku.collaboration.project.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asks")
@Slf4j
@Tag(name = "Quiz", description = "퀴즈 관련 API")
public class QuizController {

  private final QuizService quizService;
  private final QuizRepository quizRepository;

  @Operation(summary = "GPT 기반 퀴즈 생성", description = "질문 ID를 기반으로 GPT-4에게 O/X 퀴즈 5개를 받아 DB에 저장합니다.")
  @PostMapping("/{askId}/quiz")
  public ResponseEntity<BaseResponse<List<QuizCreateResponse>>> createQuiz(
      @PathVariable Long askId) {

    try {
      List<QuizCreateResponse> response = quizService.createQuizzes(askId);
      return ResponseEntity.ok(BaseResponse.success(response));
    } catch (JsonProcessingException e) {
      // 예외 로그 출력 (선택)
      log.error("GPT 퀴즈 생성 중 오류", e);
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(BaseResponse.error(500, "퀴즈 생성 중 오류가 발생했습니다."));
    }
  }

  @Operation(summary = "GPT 기반 퀴즈 시작하기", description = "퀴즈 맞추기 버튼 누르면 호출되는 API")
  @GetMapping("/{askId}/quiz/start")
  public ResponseEntity<QuizSolveResponse> startQuiz(@PathVariable Long askId) {
    List<Quiz> quizzes = quizRepository.findByAskIdOrderById(askId);

    if (quizzes.isEmpty()) {
      throw new CustomException(QuizErrorCode.QUIZ_NOT_FOUND);
    }

    Quiz firstQuiz = quizzes.get(0);
    return ResponseEntity.ok(new QuizSolveResponse(firstQuiz, 1, quizzes.size()));
  }

  @Operation(summary = "선택 시, 다음 퀴즈 불러오기", description = "사용자가 답 선택 시, 다음 퀴즈 불러오는 API")
  @PostMapping("/quiz/{quizId}/answer")
  public ResponseEntity<QuizSolveResponse> solveQuiz(
      @PathVariable Long quizId,
      @RequestParam Integer choice) {

    Quiz quiz = quizRepository.findById(quizId)
        .orElseThrow(() -> new CustomException(QuizErrorCode.QUIZ_NOT_FOUND));

    quiz.setChoice(choice); // O(1), X(0)
    quizRepository.save(quiz);

    // 다음 퀴즈 불러오기
    List<Quiz> quizzes = quizRepository.findByAskIdOrderById(quiz.getAsk().getId());
    int currentIndex = quizzes.indexOf(quiz);

    if (currentIndex + 1 >= quizzes.size()) {
      return ResponseEntity.ok(null); // 마지막 퀴즈
    }

    Quiz nextQuiz = quizzes.get(currentIndex + 1);
    return ResponseEntity.ok(new QuizSolveResponse(nextQuiz, currentIndex + 2, quizzes.size()));
  }

  @Operation(summary = "퀴즈 결과 화면", description = "퀴즈 결과 나타내는 API")
  @GetMapping("/asks/{askId}/quiz/result")
  public ResponseEntity<QuizResultResponse> showResult(@PathVariable Long askId) {
    List<Quiz> quizzes = quizRepository.findByAskIdOrderById(askId);

    int total = quizzes.size();
    int correctCount = (int) quizzes.stream()
        .filter(q -> q.getChoice() != null && q.getChoice().equals(q.getCorrect()))
        .count();

    List<QuizResultDetail> details = quizzes.stream()
        .map(q -> new QuizResultDetail(
            q.getContent(),
            q.getCorrect(),
            q.getChoice(),
            q.getChoice() != null && q.getChoice().equals(q.getCorrect())
        ))
        .toList();

    return ResponseEntity.ok(new QuizResultResponse(correctCount, total, details));
  }

}
