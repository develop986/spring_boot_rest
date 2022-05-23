package com.mysv986.spring_boot_rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "`user`")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "account", nullable = false, length = 128)
  private String account;
  @Column(name = "password", nullable = false, length = 255)
  private String password;
  @Column(name = "email", nullable = false, unique = true, length = 255)
  private String email;
  @Column(name = "admin")
  private Boolean admin;

  public static User of(String account, String password, String email) {
    return User.builder().account(account).password(password).email(email).admin(false).build();
  }

  public void merge(User user) {
    if (user.account != null && user.account.length() > 0) {
      account = user.account;
    }
    if (user.email != null && user.email.length() > 0) {
      email = user.email;
    }
    if (user.password != null && user.password.length() > 0) {
      password = user.password;
    }
    if (user.admin != null) {
      admin = user.admin;
    }
  }

}
