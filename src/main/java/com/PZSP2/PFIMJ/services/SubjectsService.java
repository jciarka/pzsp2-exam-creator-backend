package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectsService {

    @Autowired
    private ISubjectsRepository srepo;

    @Autowired
    private SubjectUsersService suservice;

    public Subject get(Long id) {
        return srepo.findById(id).orElse(null);
    }

    public Subject get(String name) {
        return srepo.findSubjectByName(name).orElse(null);
    }

    public Subject update(long id, Subject subject) {
        Subject toUpdate = srepo.findById(id).orElse(null);
        if (toUpdate != null) {
            toUpdate.setName(subject.getName());
            toUpdate.setDescription(subject.getDescription());
            return srepo.save(toUpdate);
        }
        return null;
    }

    public void delete(long id) {
        Subject toDelete = srepo.findById(id).orElse(null);
        if (toDelete != null) {
            srepo.delete(toDelete);
        }
    }

    public Subject add(Subject subject, long ownerId) {
        // add subject
        subject = srepo.save(subject);
        List<String> roles = new ArrayList<>();

        // add user with roles
        roles.add("ADMIN");
        roles.add("WRITE");
        roles.add("DELETE");
        suservice.addUserToSubject(ownerId, subject.getId(), roles);
        return subject;
    }
}
