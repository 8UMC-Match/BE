package com.sku.collaboration.project.domain.quiz.dto.response;


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
@Schema(title = "퀴즈된 퀴즈 넘겨주는 DTO")
public class QuizCreateResponse {

  private Long quizId;
  private String content;
  private Integer questionNumber;
  private Integer totalQuiz;

}
