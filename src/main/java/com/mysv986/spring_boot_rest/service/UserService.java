package com.mysv986.spring_boot_rest.service;

import com.mysv986.spring_boot_rest.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Optional<User> findByAccount(String account);

    Optional<User> findById(Long id);

    Page<User> findAll(Pageable page);

    void store(User user);

    void updateById(Long id, User user);

    void removeById(Long id);

    void updatePassword(User user, String passold, String passnew);
}
