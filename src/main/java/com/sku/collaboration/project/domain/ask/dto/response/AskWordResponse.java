package com.sku.collaboration.project.domain.ask.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "AskWordResponse DTO", description = "사용자 질문에 대한 단어 반환")
public class AskWordResponse {
    private Long askWordId;
    private String name;
    private String description;
}
