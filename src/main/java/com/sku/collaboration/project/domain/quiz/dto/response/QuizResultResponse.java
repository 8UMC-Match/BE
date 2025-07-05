package com.sku.collaboration.project.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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
@Schema(title = "퀴즈 결과 (푼 문제수 / 맞춘 개수) DTO")
public class QuizResultResponse {

  private int totalCount;        // 총 푼 문제 수
  private int correctCount;      // 맞은 문제 수
  private List<QuizResultDetail> results;  // 문제별 상세 결과 리스트
}
