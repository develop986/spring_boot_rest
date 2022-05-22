package com.mysv986.spring_boot_rest.security;

import com.mysv986.spring_boot_rest.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Userエンティティ
 */
@Service("simpleUserDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SimpleUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String account) {
        // accountでデータベースからユーザーエンティティを検索する
        return userRepository.findByAccount(account)
                .map(SimpleLoginUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

}