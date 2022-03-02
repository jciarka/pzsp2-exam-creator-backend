package com.PZSP2.PFIMJ.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.PZSP2.PFIMJ.db.entities.Role;
import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.models.auth.AddUserModel;
import com.PZSP2.PFIMJ.repositories.IRolesRepository;
import com.PZSP2.PFIMJ.repositories.IUsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService  {
  @Autowired
  IUsersRepository urepo;

  @Autowired
  IRolesRepository rrepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<User> getUsers(String email) {
    List<User> users = new ArrayList<User>();
    if (email != null) {
      urepo.findByEmailContaining(email).forEach(users::add);
    } else {
      urepo.findAll().forEach(users::add);
    }
    return users;
  }

  public User getUser(String email) {
    Optional<User> user = urepo.findByEmail(email);
    if (!user.isEmpty()) {
      return user.get();
    }
    return null;
  }

  public User getUser(long id) {
    Optional<User> user = urepo.myFindById(id);
    if (!user.isEmpty()) {
      return user.get();
    }
    return null;
  }

  public User updateUser(long id, User user) {
    Optional<User> toUpdate = urepo.findById(id);
    if (!toUpdate.isEmpty()) {
      User toUpdateClass = toUpdate.get();
      toUpdateClass.setFirstname(user.getFirstname());
      toUpdateClass.setLastname(user.getLastname());
      return urepo.save(toUpdateClass);
    }
    return null;
  }

  public void deleteUser(long id) {
    Optional<User> toDelete = urepo.findById(id);
    if (!toDelete.isEmpty()) {
      urepo.delete(toDelete.get());
    }
  }

  public boolean addToRole(long userId, long roleId) {
    Optional<User> user = urepo.findById(userId);
    Optional<Role> role = rrepo.findById(roleId);

    if (!user.isEmpty() && role != null) {
      User toUpdate = user.get(); 
      toUpdate.addRole(role.get());
      return urepo.save(toUpdate) != null;
    }
    return false;
  }

  public boolean addToRole(long userId, String roleName) {
    Optional<User> user = urepo.findById(userId);
    Optional<Role> roles = rrepo.findByName(roleName);

    if (!user.isEmpty() && !roles.isEmpty()) {
      User toUpdate = user.get(); 
      toUpdate.addRole(roles.get());
      return urepo.save(toUpdate) != null;
    }
    return false;
  }

  public boolean removeFromRole(long userId, long roleId) {
    Optional<User> user = urepo.findById(userId);
    if (user.isEmpty()) {
      return false;
    }
    User toUpdate = user.get();
    toUpdate.removeRole(roleId);
    return urepo.save(toUpdate) != null;
  }

  public boolean removeFromRole(long userId, String roleName) {
    Optional<User> user = urepo.findById(userId);
    if (user == null) {
      return false;
    }
    User toUpdate = user.get();
    toUpdate.removeRole(roleName);
    return urepo.save(toUpdate) != null;
  }

  public User createAccount(AddUserModel request) {
    Optional<User> existing = urepo.findByEmail(request.getEmail());
    if (existing.isEmpty()) {
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        
        return urepo.save(user);
    }
    return null;
  }
}
