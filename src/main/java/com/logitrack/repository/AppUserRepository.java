package com.logitrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logitrack.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByEmail(String email);
}
