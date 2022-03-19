package com.PZSP2.PFIMJ.models.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUserModel {
    private String firstname;
    private String lastname;
    private String password;
    private String email;

    public AddUserModel(String firstname, String lastname, String password, String email)
    {
        setFirstname(firstname);
        setLastname(lastname);
        setPassword(password);
        setEmail(email);
    }
}
