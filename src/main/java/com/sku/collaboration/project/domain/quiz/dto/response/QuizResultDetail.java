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
@Schema(title = "퀴즈 결과 세부사항 DTO")
public class QuizResultDetail {

  private String content;     // 퀴즈 내용
  private Integer correct;    // 정답 (0 or 1)
  private Integer choice;     // 사용자가 선택한 값 (0 or 1)
  private Boolean isCorrect;  // 정답 여부 (choice == correct)
}