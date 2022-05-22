package com.mysv986.spring_boot_rest.service.impl;

import com.mysv986.spring_boot_rest.entity.User;
import com.mysv986.spring_boot_rest.repository.UserRepository;
import com.mysv986.spring_boot_rest.service.UserService;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByAccount(String account) {
        Objects.requireNonNull(account, "user must be not null");
        return repository.findFirstByAccount(account);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Transactional(timeout = 10)
    @Override
    public void store(User user) {
        String encodedPassword = passwordEncode(user.getPassword());
        log.debug("encodedPassword " + encodedPassword);
        user.setPassword(encodedPassword);
        repository.save(user);
    }

    @Transactional(timeout = 10)
    @Override
    public void updateById(Long id, User user) {
        String encodedPassword = passwordEncode(user.getPassword());
        log.debug("encodedPassword " + encodedPassword);
        user.setPassword(encodedPassword);
        repository.findById(id).ifPresent(targetUser -> targetUser.merge(user));
    }

    @Transactional(timeout = 10)
    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Transactional(timeout = 10)
    @Override
    public void updatePassword(User user, String passold, String passnew) {
        if (!passwordEncoder.matches(passold, user.getPassword())) {
            throw new RuntimeException("password error");
        }
        user.setPassword(passwordEncode(passnew));
        repository.findById(user.getId()).ifPresent(targetUser -> targetUser.merge(user));
    }

    /**
     *
     * @param rawPassword 平文のパスワード
     * @return 暗号化されたパスワード
     */
    private String passwordEncode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}
