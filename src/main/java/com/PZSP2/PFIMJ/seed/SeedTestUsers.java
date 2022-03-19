package com.PZSP2.PFIMJ.seed;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.models.auth.AddUserModel;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import com.PZSP2.PFIMJ.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeedTestUsers {

    @Autowired
    private UsersService userv;

//    // TEST PURPOSES
//    @Autowired
//    private ISubjectsRepository srepo;
//    // TEST PURPOSES END

    @PostConstruct
    public void init() {
        List<AddUserModel>  usersToAdd = new ArrayList<>();
        usersToAdd.add(new AddUserModel("user1", "user1", "qwerty", "user1@pw.edu.pl"));
        usersToAdd.add(new AddUserModel("user2", "user2", "qwerty", "user2@pw.edu.pl"));
        usersToAdd.add(new AddUserModel("user3", "user3", "qwerty", "user3@pw.edu.pl"));

        for (AddUserModel user: usersToAdd) {
            User inDb = userv.getUser(user.getEmail());

            if (inDb == null)
            {
                userv.createAccount(user);
            }
        }

//        // TEST PURPOSES
//        List<Subject> subjects = new ArrayList<>();
//        subjects.add(new Subject("PZSP2", "Projekt zespołowy 2"));
//        subjects.add(new Subject("PZSP1", "Projekt zespołowy 1"));
//        subjects.add(new Subject("BSS", "Bezpieczeństwo systemów i sieci"));
//        subjects.add(new Subject("PAP", "Programowanie aplikacyjne"));
//        subjects.add(new Subject("WSI", "Wstęp do sztucznej inteligencji"));
//        subjects.add(new Subject("MNUM", "Metody numeryczne"));
//
//
//        // TEST PURPOSES END

    }
}
