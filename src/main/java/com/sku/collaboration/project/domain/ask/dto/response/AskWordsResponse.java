package com.sku.collaboration.project.domain.ask.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(title = "AskWordResponse DTO", description = "사용자 질문에 대한 단어 목록 반환")
public class AskWordsResponse {
    private List<AskWordResponse> data;
}
