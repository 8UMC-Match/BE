package com.sku.collaboration.project.domain.user.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Badge {

    @Schema(description = "나무")
    TREE,
    @Schema(description = "브론즈")
    BRONZE,
    @Schema(description = "실버")
    SILVER,
    @Schema(description = "골드")
    GOLD
}
