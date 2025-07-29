package com.DevWhiz.blog.service;

import com.DevWhiz.blog.domain.entity.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);

}
