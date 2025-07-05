package com.sku.collaboration.project.domain.userBadge.exception;

import com.sku.collaboration.project.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserBadgeErrorCode implements BaseErrorCode {
  BADGE_NOT_OWNED("BADGE_4001", "해당 회원이 해당 배찌를 소유하고 있지 않습니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
