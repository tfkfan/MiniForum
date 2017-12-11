package com.tfkfan.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public final class SecurityContextUtils implements Serializable {

    private static final Long serialVersionUID = 1L;

    public SecurityContextUtils() {
    }

    public static boolean isLoggedIn() {
        Authentication authentication = authentication();
        if(authentication==null) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public static boolean hasRole(UserRole type) {
        Authentication authentication = authentication();
        if(authentication==null) {
            return false;
        }
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(type.getRole());
        boolean hasRole = authentication.getAuthorities().contains(grantedAuthority);
        return hasRole;
    }

    public static User getUser() {
        Authentication authentication = authentication();
        if(authentication==null) {
            return new User();
        }
        User user = (User)authentication.getPrincipal();
        return user;
    }

    public static List<UserRole> getRoleTypes() {
        Authentication authentication = authentication();
        if(authentication==null) {
            return new ArrayList<>();
        }
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        List<UserRole> roleTypes = authorities.stream().map(x -> UserRole.findByRole(x.getAuthority())).collect(Collectors.toList());
        return roleTypes;
    }

    @SuppressWarnings("unchecked")
    private static Authentication authentication() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext==null) {
            throw new SecurityContextNotFoundException("SecurityContext Not Found Exception");
        }

        final Authentication authentication = securityContext.getAuthentication();
        if(authentication==null) {
            throw new SecurityContextNotFoundException("SecurityContext Authentication Not Found Exception");
        }
        return authentication;
    }

}
