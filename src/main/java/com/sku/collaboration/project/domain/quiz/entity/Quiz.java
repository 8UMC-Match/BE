package com.sku.collaboration.project.domain.quiz.entity;


import com.sku.collaboration.project.domain.ask.entity.Ask;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "quizzes")
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content; // 퀴즈 내용

  @Column(nullable = false)
  private Integer correct; // 정답 정보

  @Column(nullable = true)
  private Integer choice; // 선택 정보

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ask_id")
  private Ask ask;
}
