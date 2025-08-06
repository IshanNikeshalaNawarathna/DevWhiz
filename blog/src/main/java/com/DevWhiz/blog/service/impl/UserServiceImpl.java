package com.DevWhiz.blog.service.impl;

import com.DevWhiz.blog.domain.entity.User;
import com.DevWhiz.blog.repo.UserRepo;
import com.DevWhiz.blog.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public User getUserById(UUID id) {
        return userRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found With Id: " + id));
    }

}
