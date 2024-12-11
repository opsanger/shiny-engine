package com.klp.interview.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
@Schema
public record User(
        @Schema(description = "The id of the user", example = "1")
        Long id,

        @Schema(description = "The email of the user", example = "test@test.com")
        @NotNull
        @Email
        String email,

        @Schema(description = "The type of the user", example = "ADMIN", allowableValues = {"ADMIN", "USER"})
        @NotNull
        @Enumerated(EnumType.STRING)
        UserType type) {
}
