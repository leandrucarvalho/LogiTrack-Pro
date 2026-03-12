package com.logitrack.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.logitrack.model.AppUser;
import com.logitrack.model.Role;
import com.logitrack.repository.AppUserRepository;
import com.logitrack.repository.RoleRepository;

@Component
public class UserSeeder implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(AppUserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ADMIN");
                    return roleRepository.save(role);
                });

        AppUser admin = new AppUser();
        admin.setName("Administrador");
        admin.setEmail("admin@logitrack.local");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setActive(true);
        admin.setRoles(Set.of(adminRole));

        userRepository.save(admin);
    }
}
