package com.sku.collaboration.project.domain.badge.repository;

import com.sku.collaboration.project.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
