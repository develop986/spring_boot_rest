package com.mysv986.spring_boot_rest.controller;

import com.mysv986.spring_boot_rest.entity.User;
import com.mysv986.spring_boot_rest.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> id(@PathVariable(value = "id") Long id) {
        Optional<User> user = service.findById(id);
        // パスワードは隠ぺいする
        user.get().setPassword(null);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> list(Pageable page) {
        Page<User> users = service.findAll(page);
        // パスワードは隠ぺいする
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users.getContent());
    }

    @PutMapping(path = "{id}", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String update(@PathVariable(value = "id") Long id, @RequestBody User user) {
        // アカウント妥当性チェックが必要だが
        // フォームで編集をブロックしていることと
        // 万が一アカウント名が編集されても害はない
        service.updateById(id, user);
        return "success";
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String store(@RequestBody User user) {
        // アカウントの重複チェックが必要だが
        // UNIQUE KEY (account) に任せる
        service.store(user);
        return "success";
    }

    @DeleteMapping(path = "{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String remove(@PathVariable(value = "id") Long id) {
        service.removeById(id);
        return "success";
    }

}
