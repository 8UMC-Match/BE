package com.sku.collaboration.project.domain.user.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Type {

    @Schema(description = "지적장애인")
    INTELLECTUAL_DISABILITY,

    @Schema(description = "노인")
    SENIOR,

    @Schema(description = "어린이")
    CHILD,

    @Schema(description = "기타")
    ETC
}
