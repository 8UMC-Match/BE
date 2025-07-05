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
@Schema(title = "퀴즈 생성 (질문, 정답) DTO")
public class QuizPromptResponse {

  private String content; // 퀴즈 질문
  private int correct;    // 정답: 0(O), 1(X)

}
