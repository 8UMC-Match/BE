package com.sku.collaboration.project.domain.user.service;

import com.sku.collaboration.project.domain.user.dto.request.LanguageUpdateRequest;
import com.sku.collaboration.project.domain.user.dto.request.NameUpdateRequest;
import com.sku.collaboration.project.domain.user.dto.request.PasswordUpdateRequest;
import com.sku.collaboration.project.domain.user.dto.request.SignUpRequest;
import com.sku.collaboration.project.domain.user.dto.response.SignUpResponse;
import com.sku.collaboration.project.domain.user.entity.User;
import com.sku.collaboration.project.domain.user.enums.Language;
import com.sku.collaboration.project.domain.user.enums.Role;
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
        .name(request.getName())
        .language(request.getLanguage())
        .introduction(request.getIntroduction())
        .authRole(Role.USER)
        .build();

    // 저장 및 로깅
    User savedUser = userRepository.save(user);
    log.info("[서비스] 회원가입 성공: username = {}", savedUser.getUsername());
    return userMapper.toSignUpResponse(savedUser);
  }

  @Transactional
  public void changePassword(Long userId, PasswordUpdateRequest passwordUpdateRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    log.info("[서비스] 비밀번호 변경 시도: username = {}", user.getUsername());

    // 현재 비밀번호 확인
    if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), user.getPassword())) {
      log.warn("[서비스] 비밀번호가 일치하지 않습니다.: username = {}", user.getUsername());
      throw new CustomException(UserErrorCode.PASSWORD_MISMATCH);
    }

    // 새 비밀번호 인코딩
    String encodedPassword = passwordEncoder.encode(passwordUpdateRequest.getNewPassword());

    // 비밀번호 변경
    user.setPassword(encodedPassword);
    log.info("[서비스] 비밀번호 변경 성공: username = {}", user.getUsername());

  }

  @Transactional
  public void changeLanguage(Long userId, LanguageUpdateRequest newLanguage) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    log.info("[서비스] 언어 변경 시도: username = {}", user.getUsername());

    // 언어 변경
    user.setLanguage(Language.valueOf(newLanguage.getNewLanguage()));
    log.info("[서비스] 언어 변경 성공: username = {}, newLanguage = {}", user.getUsername(), newLanguage);
  }

  @Transactional
  public void changeName(Long userId, NameUpdateRequest newName) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    log.info("[서비스] 사용자 이름 변경 시도: username = {}", user.getUsername());

    // 이름 변경
    user.setName(newName.getNewName());
    log.info("[서비스] 사용자 이름 변경 성공: username = {}, newName = {}", user.getUsername(), newName);
  }
}
