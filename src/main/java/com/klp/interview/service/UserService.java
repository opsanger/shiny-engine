package com.klp.interview.service;

import com.klp.interview.model.UserSpec;
import com.klp.interview.repository.UserEntity;
import com.klp.interview.model.UserType;
import com.klp.interview.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepoitory;

    public UserService(UserRepository userRepoitory) {
        this.userRepoitory = userRepoitory;
    }

    public UserEntity getUser(Long id) {
        return userRepoitory.findById(id).orElse(null);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepoitory.save(user);
    }

    public List<UserEntity> findUsers(String id, String email, String type) {
        Specification<UserEntity> spec = getUserSpec(id, email, type);
        return userRepoitory.findAll(spec);
    }

    private Specification<UserEntity> getUserSpec(String id, String email, String type) {
        Specification<UserEntity> specification = Specification.where(null);
        if(id != null) {
            specification = specification.and(UserSpec.hasId(Long.valueOf(id)));
        }
        if(email != null) {
            specification = specification.and(UserSpec.hasEmail(email));
        }
        if(type != null) {
            specification = specification.and(UserSpec.hasType(UserType.valueOf(type)));
        }

        return specification;
    }
}
