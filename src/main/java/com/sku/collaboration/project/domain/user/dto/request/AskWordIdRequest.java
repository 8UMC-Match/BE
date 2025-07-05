package com.sku.collaboration.project.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(title = "AskWordIdRequest DTO", description = "사용자 단어장 저장을 위한 단어 아이디 리스트")
public class AskWordIdRequest {
    @Schema(description = "사용자가 선택한 단어들 id값", example = "[1, 2, 3]")
    private List<Long> askwordIds;
}
