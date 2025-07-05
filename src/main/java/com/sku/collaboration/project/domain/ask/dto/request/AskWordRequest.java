package com.sku.collaboration.project.domain.ask.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
@Schema(title = "AskWordRequest DTO", description = "사용자의 특정 단어 질문을 위한 DTO")
public class AskWordRequest {
    @Schema(description = "사용자 질문 단어", example = "병행")
    private String name;
}
