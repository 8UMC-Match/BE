package com.sku.collaboration.project.domain.user.service;

import com.sku.collaboration.project.domain.ask.repository.AskRepository;
import com.sku.collaboration.project.domain.user.dto.request.SignUpRequest;
import com.sku.collaboration.project.domain.user.dto.response.SignUpResponse;
import com.sku.collaboration.project.domain.user.dto.response.BadgeResponse;
import com.sku.collaboration.project.domain.user.entity.User;
import com.sku.collaboration.project.domain.user.exception.UserErrorCode;
import com.sku.collaboration.project.domain.user.mapper.UserMapper;
import com.sku.collaboration.project.domain.user.repository.UserRepository;
import com.sku.collaboration.project.domain.word.dto.response.WordResponse;
import com.sku.collaboration.project.domain.word.repository.WordRepository;
import com.sku.collaboration.project.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sku.collaboration.project.domain.user.enums.Badge;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final AskRepository askRepository;
  private final WordRepository wordRepository;
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

  @Transactional(readOnly = true)
  public BadgeResponse getBadge(User user) {
    int questionCount = askRepository.countByUserId(user.getId());
    int dictionaryCount = wordRepository.countByUserId(user.getId());

    Badge badge;
    double progressRate;

    if (questionCount >= 100 && dictionaryCount >= 100) {
      badge = Badge.GOLD;
      progressRate = 1.0;
    } else if (questionCount >= 50 && dictionaryCount >= 50) {
      badge = Badge.SILVER;
      int q = Math.max(0, questionCount - 50);
      int d = Math.max(0, dictionaryCount - 50);
      progressRate = Math.min(q, d) / 50.0;
    } else if (questionCount >= 1 && dictionaryCount >= 1) {
      badge = Badge.BRONZE;
      progressRate = Math.min(questionCount, dictionaryCount) / 50.0;
    } else {
      badge = Badge.TREE;
      progressRate = Math.min(questionCount, dictionaryCount) / 50.0;
    }

    return BadgeResponse.builder()
        .badge(badge)
        .askCount(questionCount)
        .wordCount(dictionaryCount)
        .progressRate(Math.min(progressRate, 1.0))
        .build();
  }

  @Transactional(readOnly = true)
  public List<WordResponse> getUserVocabulary(User user) {
    return wordRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId()).stream()
        .map(word -> WordResponse.builder()
            .wordId(word.getId())
            .name(word.getName())
            .description(word.getDescription())
            .createdAt(word.getCreatedAt())
            .build())
        .toList();
  }

}
