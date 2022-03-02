package com.PZSP2.PFIMJ.models.auth;

import com.PZSP2.PFIMJ.db.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse extends UserModel {

    private String token;

    public AuthResponse(User user, String token) {
        super(user);
        this.token = token;
    }
}
