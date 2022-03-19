package com.PZSP2.PFIMJ.seed;

import com.PZSP2.PFIMJ.db.entities.SubjectRole;
import com.PZSP2.PFIMJ.repositories.ISubjectsRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeedSubjectRoles {

    @Autowired
    private ISubjectsRolesRepository srrepo;

    @PostConstruct
    public void init() {
        List<SubjectRole> subjectRoles = new ArrayList<>();
        subjectRoles.add(new SubjectRole("READ"));
        subjectRoles.add(new SubjectRole("WRITE"));
        subjectRoles.add(new SubjectRole("ADMIN"));

        for (SubjectRole role :  subjectRoles) {
            SubjectRole inDb = srrepo.findByName(role.getName()).orElse(null);

            if (inDb == null)
            {
                srrepo.save(role);
            }
        }
    }
}
