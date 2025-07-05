package com.sku.collaboration.project.domain.auth.dto.response;

import com.sku.collaboration.project.domain.user.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "LoginResponse DTO", description = "사용자 로그인에 대한 응답 반환")
public class LoginResponse {

  @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String accessToken;

  @Schema(description = "사용자 ID", example = "1")
  private Long userId;

  @Schema(description = "이메일", example = "user@example.com")
  private String username;

  @Schema(description = "닉네임", example = "easyreader123")
  private String nickname;

  @Schema(description = "레벨", example = "1")
  private Integer level;

  @Schema(description = "장애 유형", example = "지적장애", nullable = true)
  private Type type;

  @Schema(description = "토큰 만료 시간(ms)", example = "1800000")
  private Long expirationTime;

  @Schema(description = "계정 생성일", example = "2025-07-05T14:30:00")
  private LocalDateTime createdAt;

  @Schema(description = "최종 수정일", example = "2025-07-05T15:30:00", nullable = true)
  private LocalDateTime modifiedAt;
}
