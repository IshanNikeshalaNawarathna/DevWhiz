package com.DevWhiz.blog.config;

import com.DevWhiz.blog.domain.entity.User;
import com.DevWhiz.blog.repo.UserRepo;
import com.DevWhiz.blog.security.BlogUserDetailService;
import com.DevWhiz.blog.security.JwtAuthenticationFilter;
import com.DevWhiz.blog.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService){
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo){
        BlogUserDetailService blogUserDetailService = new BlogUserDetailService(userRepo);

        String email = "test@gmail.com";
        userRepo.findByEmail(email).orElseGet(()->{
            User saveUser = User.builder()
                    .name("test")
                    .email(email)
                    .password(passwordEncoder().encode("1122"))
                    .build();
            return userRepo.save(saveUser);
        });

        return blogUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http.authorizeHttpRequests(auth->auth
                .requestMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/categories/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/posts/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/posts/drafts").authenticated()
                .requestMatchers(HttpMethod.GET,"/api/v1/tags/**").permitAll()
        ).csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
