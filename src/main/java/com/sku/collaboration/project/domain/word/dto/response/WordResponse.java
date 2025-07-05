package com.sku.collaboration.project.domain.word.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WordResponse {
  private Long wordId;
  private String name;
  private String description;
}