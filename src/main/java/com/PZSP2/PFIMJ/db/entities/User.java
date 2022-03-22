package com.PZSP2.PFIMJ.db.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
      name = "users",
      uniqueConstraints = {
              @UniqueConstraint(name = "email_unique_k", columnNames = {"email"})
      },
      indexes = {
          @Index(columnList = "email", name = "user_email_ix")
      }
)
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
  @Column(name = "user_id", columnDefinition = "NUMBER(9,0)")
  private Long id = 0L;

  @Column(length = 100)
  private String firstname;
  @Column(nullable = false, length = 100)
  private String lastname;
  @Column(nullable = false, length = 100)
  private String email;
  @Column(length = 100)
  private String position;
  @Column(length = 100)
  private String academicTitle;
  @Column(length = 100)
  private String institute;
  @Column(length = 100)
  private String faculty;

  @ManyToMany(
          fetch = FetchType.EAGER,
          cascade = {
            CascadeType.REMOVE,
            CascadeType.REMOVE
          }
  )
  @JoinTable(
          name = "users_global_roles",
          indexes = {
              @Index(columnList = "user_id", name = "global_roles_to_user_ix"),
              @Index(columnList = "role_id", name = "global_roles_to_role_ix")
          },
          joinColumns = {
              @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "global_roles_to_user_fk"))
          },
          inverseJoinColumns = {
              @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "global_roles_to_role_fk"))
          }
          )
  private Set<Role> roles;

  @Column(nullable = false, length = 100)
  private String password;

  @OneToMany(mappedBy = "user")
  private Set<SubjectUser> subjectUsers = new HashSet<>();

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
