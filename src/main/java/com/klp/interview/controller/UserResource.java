package com.klp.interview.controller;

import com.klp.interview.model.User;
import com.klp.interview.repository.UserEntity;
import com.klp.interview.service.UserMapper;
import com.klp.interview.service.UserService;
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

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        UserEntity userEntity = userService.getUser(id);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(userEntity));
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> findUsers(@RequestParam(required = false) String id,
                                                @RequestParam(required = false) String email,
                                                @RequestParam(required = false) String type) {
        if (!validInput(id, email, type)) {
            return ResponseEntity.badRequest().build();
        }
        List<UserEntity> users = userService.findUsers(id, email, type);
        List<User> usersDto = users.stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping("/user")
    public User createUser(@Validated @RequestBody User user) {
        UserEntity userEntity = userService.saveUser(userMapper.toEntity(user));
        return userMapper.toDto(userEntity);
    }

    private boolean validInput(String id, String email, String type) {
        return (id != null && id.matches("\\d+")) ||
                (email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) ||
                (type != null && type.matches("ADMIN|USER"));
    }
}
