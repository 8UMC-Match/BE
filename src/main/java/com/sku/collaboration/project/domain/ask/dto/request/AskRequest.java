package com.sku.collaboration.project.domain.ask.dto.request;

import com.sku.collaboration.project.domain.chat.dto.Message;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Data
@Schema(title = "AskRequest DTO", description = "사용자 질문을 위한 DTO")
public class AskRequest {
    @Schema(description = "사용자 질문", example = "요즘처럼 여름철이 되면 날씨가 매우 덥고 습해집니다. 기온이 높고 습도가 높을수록 우리 몸은 쉽게 피곤해지고, 땀이 많이 나기 때문에 탈수 증상이 생기기 쉬워요. 특히 \n" +
            "햇볕이 강한 낮 시간에는 밖에 나가는 것이 힘들 수 있어요. 그래서 햇빛이 강한 날에는 외출을 하기 전에 꼭 선크림을 얼굴과 팔, 목 같은 노출된 피부에 꼼꼼히 발라야 해요. 또한 햇빛을 막아주는 모자나 양산을 준비해서 사용하는 것이 매우 좋아요. 그리고 \n" +
            "더운 날에는 땀이 많이 나기 때문에 물을 \n" +
            "충분히 마시는 것도 중요해요. 특히 갈증이 생긴 후에 마시는 것보다, 갈증이 생기기 \n" +
            "전에 미리 조금씩 자주 마시는 것이 더 \n" +
            "효과적이에요. 실내에서도 너무 더우면 \n" +
            "몸이 축 처지고 기운이 없을 수 있어요. \n" +
            "이럴 땐 선풍기나 에어컨을 적절히 사용하고, 바람이 통할 수 있게 창문을 열어 두는 것도 좋아요. 이렇게 날씨가 더울 때는 몸을 시원하게 하고, 탈수되지 않도록 조심하며, 햇빛으로부터 피부를 보호하는 것이 \n" +
            "중요합니다. ")
    private String question;
}
