package com.mysv986.spring_boot_rest.security;

import com.mysv986.spring_boot_rest.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

public class SimpleLoginUser extends org.springframework.security.core.userdetails.User {

    // Userエンティティ
    private com.mysv986.spring_boot_rest.entity.User user;

    public User getUser() {
        return user;
    }

    public SimpleLoginUser(User user) {
        super(user.getAccount(), user.getPassword(), determineRoles(user.getAdmin()));
        this.user = user;
    }

    private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

    private static List<GrantedAuthority> determineRoles(boolean isAdmin) {
        return isAdmin ? ADMIN_ROLES : USER_ROLES;
    }
}
