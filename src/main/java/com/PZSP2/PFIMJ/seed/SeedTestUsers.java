package com.PZSP2.PFIMJ.seed;

import com.PZSP2.PFIMJ.db.entities.Role;
import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.SubjectUser;
import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.models.auth.AddUserModel;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import com.PZSP2.PFIMJ.services.SubjectUsersService;
import com.PZSP2.PFIMJ.services.SubjectsService;
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

    // TEST PURPOSES
    @Autowired
    private SubjectsService sservice;

    @Autowired
    private SubjectUsersService suservice;
    // TEST PURPOSES END

    @PostConstruct
    public void init() {
        List<AddUserModel>  usersToAdd = new ArrayList<>();
        usersToAdd.add(new AddUserModel("user1", "admin", "qwerty", "user1@pw.edu.pl"));
        usersToAdd.add(new AddUserModel("user2", "writer", "qwerty", "user2@pw.edu.pl"));
        usersToAdd.add(new AddUserModel("user3", "deleter", "qwerty", "user3@pw.edu.pl"));
        usersToAdd.add(new AddUserModel("user4", "writerAndDeleter", "qwerty", "user4@pw.edu.pl"));


        List<User> addedUsers = new ArrayList<>();
        for (AddUserModel user: usersToAdd) {
            User inDb = userv.getUser(user.getEmail());

            if (inDb == null)
            {
                addedUsers.add(userv.createAccount(user));
            }
        }

        // TEST PURPOSES
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("PZSP2", "Projekt zespołowy 2"));
        subjects.add(new Subject("PZSP1", "Projekt zespołowy 1"));
//        subjects.add(new Subject("BSS", "Bezpieczeństwo systemów i sieci"));
        subjects.add(new Subject("PAP", "Programowanie aplikacyjne"));
        subjects.add(new Subject("WSI", "Wstęp do sztucznej inteligencji"));
        subjects.add(new Subject("MNUM", "Metody numeryczne"));


        // TEST PURPOSES END
        for (Subject subject: subjects) {
            Subject inDb = sservice.get(subject.getName());

            if (inDb == null)
            {
                List<String> canWrite = new ArrayList<>();
                canWrite.add("WRITE");

                List<String> canWriteAndDelete = new ArrayList<>();
                canWriteAndDelete.add("DELETE");
                canWriteAndDelete.add("WRITE");

                List<String> canDelete = new ArrayList<>();
                canDelete.add("DELETE");

                Subject added = sservice.add(subject, addedUsers.get(0).getId());
                suservice.addUserToSubject(addedUsers.get(1).getId(), added.getId(), canWrite);
                suservice.addUserToSubject(addedUsers.get(2).getId(), added.getId(), canDelete);
                suservice.addUserToSubject(addedUsers.get(3).getId(), added.getId(), canWriteAndDelete);
            }
        }
    }
}
