package com.sku.collaboration.project.domain.userBadge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(title = "BadgeDetailResponse: 획득한 배찌 개별 조회 응답 DTO") //스웨서 문서화를 위한 어노테이션!
public class BadgeDetailResponse {

  @Schema(description = "배찌 아이디", example = "1")
  private Long badgeId;

  @Schema(description = "배찌 이름", example = "성장 배찌")
  private String name;

  @Schema(description = "배찌 설명", example = "현재 읽기 능력이 향상되고 있어요!")
  private String description;

  @Schema(description = "배찌 아이디", example = "획득날짜")
  private LocalDateTime createdAt;
}
