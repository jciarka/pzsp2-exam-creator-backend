package com.PZSP2.PFIMJ.models.auth;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.PZSP2.PFIMJ.db.entities.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserDetails implements UserDetails {

    private static final long serialVersionUID = 1;
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public AppUserDetails(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.password = user.getPassword();
        this.email = user.getEmail();

        this.authorities = user.getRoles()
                .stream()
                .map(x -> x.getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
}
