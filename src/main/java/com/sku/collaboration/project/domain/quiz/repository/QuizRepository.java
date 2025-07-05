package com.sku.collaboration.project.domain.quiz.repository;

import com.sku.collaboration.project.domain.quiz.entity.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

  List<Quiz> findByAskIdOrderById(Long askId);
}
