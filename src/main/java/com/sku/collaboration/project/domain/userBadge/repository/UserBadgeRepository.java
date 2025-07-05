package com.sku.collaboration.project.domain.userBadge.repository;

import com.sku.collaboration.project.domain.userBadge.entity.UserBadge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

  List<UserBadge> findByUserIdOrderByCreatedAtDesc(Long userId);

  Optional<UserBadge> findByUserIdAndBadgeId(Long userId, Long badgeId);
}
