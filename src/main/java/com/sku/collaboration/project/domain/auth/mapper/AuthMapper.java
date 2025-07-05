package com.sku.collaboration.project.domain.auth.mapper;

import com.sku.collaboration.project.domain.auth.dto.response.LoginResponse;
import com.sku.collaboration.project.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

  public LoginResponse toLoginResponse(User user, String accessToken, Long expirationTime) {
    return LoginResponse.builder()
        .accessToken(accessToken)
        .userId(user.getId())
        .username(user.getUsername())
        .nickname(user.getNickname())
        .level(String.valueOf(user.getLevel()))
        .role(user.getType())
        .expirationTime(expirationTime)
        .build();
  }
}
