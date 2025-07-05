package com.sku.collaboration.project.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.sku.collaboration.project.domain.user.enums.Badge;
import com.sku.collaboration.project.domain.user.enums.Role;
import com.sku.collaboration.project.domain.user.enums.Type;
import com.sku.collaboration.project.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username; // 이메일

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String nickname;

  @Column(name = "badge", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Badge badge = Badge.TREE;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Type type = Type.ETC;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Role role = Role.USER;

  @JsonIgnore
  @Column(name = "refresh_token")
  private String refreshToken;

  public void createRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
