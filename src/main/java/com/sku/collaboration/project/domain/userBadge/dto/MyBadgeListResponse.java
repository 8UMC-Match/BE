package com.sku.collaboration.project.domain.userBadge.dto;

import com.sku.collaboration.project.domain.userBadge.entity.UserBadge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "MyBadgeListResponse: 획득한 배찌 전체 조회 응답 DTO") //스웨서 문서화를 위한 어노테이션!
public class MyBadgeListResponse {

  @Schema(description = "배찌 아이디", example = "1")
  private final Long badgeId;

  @Schema(description = "배지 이름", example = "성장배찌")
  private final String name;

  public MyBadgeListResponse(UserBadge userBadge) {
    this.badgeId = userBadge.getBadge().getId();
    this.name = userBadge.getBadge().getName();
  }

}
