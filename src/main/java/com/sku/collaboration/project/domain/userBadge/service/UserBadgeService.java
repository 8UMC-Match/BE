package com.sku.collaboration.project.domain.userBadge.service;

import com.sku.collaboration.project.domain.userBadge.dto.BadgeDetailResponse;
import com.sku.collaboration.project.domain.userBadge.dto.MyBadgeListResponse;
import com.sku.collaboration.project.domain.userBadge.entity.UserBadge;
import com.sku.collaboration.project.domain.userBadge.exception.UserBadgeErrorCode;
import com.sku.collaboration.project.domain.userBadge.repository.UserBadgeRepository;
import com.sku.collaboration.project.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserBadgeService {

  private final UserBadgeRepository userBadgeRepository;

  public List<MyBadgeListResponse> getMyBadges(Long userId) {
    List<UserBadge> userBadgeList = userBadgeRepository.findByUserIdOrderByCreatedAtDesc(userId);
    return userBadgeList.stream()
        .map(MyBadgeListResponse::new)
        .toList();
  }

  public BadgeDetailResponse getBadgeDetail(Long badgeId) {
    Long userId = 1L; // 로그인 미구현으로 임시 사용자 ID 고정

    UserBadge userBadge = userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId)
        .orElseThrow(() -> new CustomException(UserBadgeErrorCode.BADGE_NOT_OWNED));

    return new BadgeDetailResponse(
        userBadge.getBadge().getId(),
        userBadge.getBadge().getName(),
        userBadge.getBadge().getDescription(),
        userBadge.getCreatedAt()
    );
  }

}
