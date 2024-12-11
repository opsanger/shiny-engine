package com.klp.interview.controller;

import com.klp.interview.model.User;
import com.klp.interview.model.UserType;
import com.klp.interview.repository.UserEntity;
import com.klp.interview.service.UserMapper;
import com.klp.interview.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class UserResource {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserResource(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        UserEntity userEntity = userService.getUser(id);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(userEntity));
    }

    @Operation(summary = "Find users by id, email and/or type")
    @GetMapping("/user")
    public ResponseEntity<List<User>> findUsers(@Validated @Positive @RequestParam(required = false) String id,
                                                @Validated @Email @RequestParam(required = false) String email,
                                                @RequestParam(required = false) UserType type) {
        List<UserEntity> users = userService.findUsers(id, email, type);
        List<User> usersDto = users.stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(usersDto);
    }

    @Operation(summary = "Create a new user")
    @PostMapping("/user")
    public User createUser(@Validated @RequestBody User user) {
        UserEntity userEntity = userService.saveUser(userMapper.toEntity(user));
        return userMapper.toDto(userEntity);
    }
}
