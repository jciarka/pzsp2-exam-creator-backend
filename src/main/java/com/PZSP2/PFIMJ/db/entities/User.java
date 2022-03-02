package com.PZSP2.PFIMJ.db.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "appusers")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
  private Long id = 0L;
  private String firstname;

  @Column(nullable = false)
  private String lastname;

  @Column(unique = true, nullable = false)
  private String email;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(name = "appuserroles", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = {
      @JoinColumn(name = "roleid") })
      
  private Set<Role> roles;

  private String password;

  public void addRole(Role role) {
    this.roles.add(role);
    role.getUsers().add(this);
  }

  public void removeRole(long roleId) {
    Role role = this.roles.stream().filter(t -> t.getId() == roleId).findFirst().orElse(null);
    if (role != null)
      this.roles.remove(role);
    role.getUsers().remove(this);
  }

  public void removeRole(String roleName) {
    Role role = this.roles.stream().filter(t -> t.getName().equals(roleName)).findFirst().orElse(null);
    if (role != null)
      this.roles.remove(role);
    role.getUsers().remove(this);
  }
}
