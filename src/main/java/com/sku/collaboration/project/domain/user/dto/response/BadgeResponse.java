package com.sku.collaboration.project.domain.user.dto.response;

import com.sku.collaboration.project.domain.user.enums.Badge;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BadgeResponse {
  private Badge badge;          // 현재 등급
  private int askCount;        // 질문 수
  private int wordCount;      // 단어장 수
  private double progressRate;      // 다음 등급까지의 진행률 (0.0 ~ 1.0)
}
