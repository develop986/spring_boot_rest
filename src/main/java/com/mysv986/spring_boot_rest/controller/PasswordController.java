package com.mysv986.spring_boot_rest.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.mysv986.spring_boot_rest.entity.User;
import com.mysv986.spring_boot_rest.service.UserService;

@RestController
@RequestMapping(path = "password")
@Slf4j
public class PasswordController {

    private final UserService service;

    public PasswordController(UserService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String update(HttpServletRequest request, @RequestParam String passold,
            @RequestParam String passnew, Principal principal) {
        log.debug(request.toString());
        log.debug("passold " + passold);
        log.debug("passnew " + passnew);
        log.debug("principal " + principal);
        String name = principal.getName();
        log.debug("name " + name);
        Optional<User> user = service.findByAccount(name);
        log.debug("user " + user);
        if (user.isEmpty()) {
            throw new RuntimeException("could not get a user.");
        }
        service.updatePassword(user.get(), passold, passnew);
        return "success";
    }

}
