package com.PZSP2.PFIMJ.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.PZSP2.PFIMJ.core.security.JwtTokenUtil;
import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.models.Response;
import com.PZSP2.PFIMJ.models.auth.AddUserModel;
import com.PZSP2.PFIMJ.models.auth.AppUserDetails;
import com.PZSP2.PFIMJ.models.auth.AuthRequest;
import com.PZSP2.PFIMJ.models.auth.AuthResponse;
import com.PZSP2.PFIMJ.models.auth.UserModel;
import com.PZSP2.PFIMJ.services.RolesService;
import com.PZSP2.PFIMJ.services.UsersService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/auth/")
public class AuthController extends ControllerBase {
  final UsersService uservice;
  final RolesService rservice;

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UsersService userService;

  public AuthController(AuthenticationManager authenticationManager,
                        JwtTokenUtil jwtTokenUtil,
                        UsersService userService, UsersService uservice, RolesService rservice) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userService = userService;
    this.uservice = uservice;
    this.rservice = rservice;
  }

  @PostMapping("login")
  public ResponseEntity<Response<AuthResponse>> login(@RequestBody AuthRequest request) {

    try {
      Authentication authenticate = authenticationManager
          .authenticate(
              new UsernamePasswordAuthenticationToken(
                  request.getEmail(), request.getPassword()));

      AppUserDetails userDetails = (AppUserDetails) authenticate.getPrincipal();

      String token = jwtTokenUtil.generateAccessToken(userDetails);

      AuthResponse model = new AuthResponse(
          this.userService.getUser(userDetails.getUsername()),
          token);

      return ResponseEntity.ok().body(new Response<>(model));

    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("register")
  public ResponseEntity<Response<UserModel>> createAccount(@RequestBody AddUserModel request) {

      User user = userService.createAccount(request);

      if (user != null) {
        return ResponseEntity.ok()
          .body(new Response<>(new UserModel(user)));
      }
      
      ArrayList<String> errors = new ArrayList<>();
      errors.add("Already exists account using this emial");
      return ResponseEntity.ok().body(new Response<>(null, false, errors));
  }

  @GetMapping()
  public ResponseEntity<Response<List<UserModel>>> getAll() {
    List<User> users = uservice.getUsers(null);
    List<UserModel> models = users.stream().map(UserModel::new).collect(Collectors.toList());
    return new ResponseEntity<>(new Response<>(models), HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<Response<UserModel>> getById(@PathVariable("id") long id) {
    User user = uservice.getUser(id);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(new Response<>(new UserModel(user)), HttpStatus.OK);
  }

  @PutMapping("{userId}/addrole/{role}")
  public ResponseEntity<User> addToRole(@PathVariable("userId") long userId, @PathVariable("role") String role) {
    boolean success = uservice.addToRole(userId, role);
    if (!success) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("{userId}/removerole/{role}")
  public ResponseEntity<User> removeFromRole(@PathVariable("userId") long userId, @PathVariable("role") String role) {
    boolean success = uservice.removeFromRole(userId, role);
    if (!success) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

// Based on
// https://github.com/Yoh0xFF/java-spring-security-example/blob/master/src/main/java/io/example/service/UserService.java
// https://www.toptal.com/spring/spring-security-tutorial
