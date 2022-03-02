package com.PZSP2.PFIMJ.models.auth;

import java.util.List;
import java.util.stream.Collectors;
import com.PZSP2.PFIMJ.db.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

  private Long id;
  private String firstname;
  private String lastname;
  private String emial;
  private List<String> roles;

  public UserModel(User user) {
    this.id = user.getId();
    this.firstname = user.getFirstname();
    this.lastname = user.getLastname();
    this.emial = user.getEmail();
    if (user.getRoles() != null) {
      this.roles = user.getRoles() 
      .stream()
      .map(x -> x.getName())
      .collect(Collectors.toList());
    }
  }
}
