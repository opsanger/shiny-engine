package com.klp.interview.service;

import com.klp.interview.model.User;
import com.klp.interview.repository.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toDto(UserEntity entity) {
        return new User(entity.getId(), entity.getEmail(), entity.getType());
    }

    public UserEntity toEntity(User dto) {
        return new UserEntity(dto.id(), dto.email(), dto.type());
    }
}
