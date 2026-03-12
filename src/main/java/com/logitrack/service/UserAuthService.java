package com.logitrack.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.logitrack.model.AppUser;
import com.logitrack.repository.AppUserRepository;

@Service
public class UserAuthService implements UserDetailsService {

    private final AppUserRepository userRepository;

    public UserAuthService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();

        return new User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
    }
}
