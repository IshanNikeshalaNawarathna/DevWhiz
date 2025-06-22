package com.DevWhiz.blog.security;

import com.DevWhiz.blog.domain.entity.User;
import com.DevWhiz.blog.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class BlogUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found with email: "+email));

        return new BlogUserDetails(user);
    }
}
