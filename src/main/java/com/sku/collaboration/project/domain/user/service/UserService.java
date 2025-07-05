package com.sku.collaboration.project.domain.user.service;

import com.sku.collaboration.project.domain.user.dto.request.SignUpRequest;
import com.sku.collaboration.project.domain.user.dto.response.SignUpResponse;
import com.sku.collaboration.project.domain.user.entity.User;
import com.sku.collaboration.project.domain.user.exception.UserErrorCode;
import com.sku.collaboration.project.domain.user.mapper.UserMapper;
import com.sku.collaboration.project.domain.user.repository.UserRepository;
import com.sku.collaboration.project.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  @Transactional
  public SignUpResponse signUp(SignUpRequest request) {
    log.info("[서비스] 회원가입 시도: username = {}", request.getUsername());
    if (userRepository.existsByUsername(request.getUsername())) {
      log.warn("[서비스] 이미 존재하는 사용자: username = {}", request.getUsername());
      throw new CustomException(UserErrorCode.USERNAME_ALREADY_EXISTS);
    }

    // 비밀번호 인코딩
    String encodedPassword = passwordEncoder.encode(request.getPassword());

    // 유저 엔티티 생성
    User user = User.builder()
        .username(request.getUsername())
        .password(encodedPassword)
        .nickname(request.getNickname())
        .build();

    // 저장 및 로깅
    User savedUser = userRepository.save(user);
    log.info("[서비스] 회원가입 성공: username = {}", savedUser.getUsername());
    return userMapper.toSignUpResponse(savedUser);
  }


}
