package com.group1.gestao_seguranca.security;

import com.group1.gestao_seguranca.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthUtils {
    public User getCurrentUser() {
        return (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

    public String getCurrentUserName() {
        return getCurrentUser().getUsername();
    }
}
