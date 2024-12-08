package com.klp.interview.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.lang.NonNull;

public record User(Long id, String email, @NonNull @Enumerated(EnumType.STRING) UserType type) {
}
