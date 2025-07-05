package com.sku.collaboration.project.domain.quiz.dto.response;

import com.sku.collaboration.project.domain.quiz.entity.Quiz;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "퀴즈 풀이 DTO")
public class QuizSolveResponse {

  private Long quizId;        // 퀴즈 ID (클라이언트가 답변할 때 필요)
  private String content;     // 퀴즈 내용
  private Integer quizNumber; // 현재 퀴즈 번호 (1번, 2번...)
  private Integer totalCount; // 전체 퀴즈 개수

  public QuizSolveResponse(Quiz quiz, int quizNumber, int totalCount) {
    this.quizId = quiz.getId();
    this.content = quiz.getContent();
    this.quizNumber = quizNumber;
    this.totalCount = totalCount;
  }

}
