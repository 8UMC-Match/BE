package com.sku.collaboration.project.domain.ask.repository;

import com.sku.collaboration.project.domain.ask.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask, Long> {
}
