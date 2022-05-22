package com.mysv986.spring_boot_rest.repository;

import com.mysv986.spring_boot_rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount(String account);
    Optional<User> findFirstByAccount(String account);
}
