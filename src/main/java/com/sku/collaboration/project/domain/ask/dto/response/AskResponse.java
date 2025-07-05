package com.sku.collaboration.project.domain.ask.dto.response;

import com.sku.collaboration.project.domain.chat.dto.Message;
import com.sku.collaboration.project.domain.chat.dto.response.ChatResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Data
@Schema(title = "AskResponse DTO", description = "사용자 질문에 대한 응답 반환")
public class AskResponse {
    @Schema(description = "질문 ID")
    private Long questionId;

    @Schema(description = "AI 답변 내용")
    private String content;
}
