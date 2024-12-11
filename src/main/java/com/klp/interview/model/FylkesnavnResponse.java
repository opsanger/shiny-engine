package com.klp.interview.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record FylkesnavnResponse(
        @Schema(description = "The name of the county")
        String fylkesnavn) {

}
