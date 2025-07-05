package com.sku.collaboration.project.domain.user.dto.response;

import com.sku.collaboration.project.domain.user.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "SignUpResponse DTO", description = "사용자 회원가입에 대한 응답 반환")
public class SignUpResponse {

  @Schema(description = "회원가입된 사용자 ID", example = "1")
  private Long userId;

  @Schema(description = "회원가입된 사용자 아이디", example = "jaeyeon20@gmail.com")
  private String username;

  @Schema(description = "회원가입된 사용자 이름", example = "재연")
  private String nickname;

  @Schema(description = "회원가입된 사용자 유형", example = "INTELLECTUAL_DISABILITY")
  private Type type;

}
