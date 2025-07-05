package com.sku.collaboration.project.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.sku.collaboration.project.domain.user.enums.Type;
import com.sku.collaboration.project.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private Integer level;

  @Enumerated(EnumType.STRING)
  @Column(nullable = true)
  private Type type;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column
  private LocalDateTime modifiedAt;

  // Getters and setters...

  @JsonIgnore
  @Column(name = "refresh_token")
  private String refreshToken;

  public void createRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
