package com.klp.interview.model;


import com.klp.interview.repository.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {

    private UserSpec() {
    }

    public static Specification<UserEntity> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<UserEntity> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<UserEntity> hasType(UserType type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }
}
