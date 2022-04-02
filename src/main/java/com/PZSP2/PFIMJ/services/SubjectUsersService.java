package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.db.entities.*;
import com.PZSP2.PFIMJ.repositories.ISubjectUsersRepository;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import com.PZSP2.PFIMJ.repositories.ISubjectsRolesRepository;
import com.PZSP2.PFIMJ.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectUsersService {

    @Autowired
    private ISubjectUsersRepository surepo;

    @Autowired
    private ISubjectsRepository srepo;

    @Autowired
    private IUsersRepository urepo;

    @Autowired
    private ISubjectsRolesRepository srrepo;

    public SubjectUser get(Long userId, Long subjecId)
    {
        return surepo.findByUserIdAndSubjectId(userId, subjecId).orElse(null);
    }

    public List<SubjectUser> get(Long userId)
    {
        return surepo.findAllByUserId(userId);
    }

    public SubjectUser addUserToSubject(Long userId, Long subjectId)
    {
        User user = urepo.findById(userId).orElse(null);
        Subject subject = srepo.findById(subjectId).orElse(null);
        if (subject == null || user == null)
            return null;

        SubjectUser subjectUser = new SubjectUser(user, subject);

        return surepo.save(subjectUser);
    }

    public SubjectUser addUserToSubject(Long userId, Long subjectId, List<String> roles)
    {
        SubjectUser sUser = addUserToSubject(userId, subjectId);
        if (sUser == null) return null;

        for (String role : roles) {
            addUserToRole(userId, subjectId, role);
        }
        return  sUser;
    }

    public SubjectUser addUserToSubject2(Long userId, Long subjectId, List<Long> rolesId)
    {
        SubjectUser sUser = addUserToSubject(userId, subjectId);
        if (sUser == null) return null;

        for (Long roleid : rolesId) {
            addUserToRole(userId, subjectId, roleid);
        }
        return sUser;
    }

    public void removeUserFromSubject(Long userId, Long subjectId)
    {
        SubjectUser sUser = surepo.findByUserIdAndSubjectId(userId, subjectId).orElse(null);
        if (sUser == null) return;

        surepo.delete(sUser);
    }

    public SubjectUser addUserToRole(Long userId, Long subjectId, String roleName)
    {
        SubjectUser sUser = surepo.findByUserIdAndSubjectId(userId, subjectId).orElse(null);
        SubjectRole role = srrepo.findByName(roleName).orElse(null);

        if (sUser == null || roleName == null)
            return null;

        sUser.getRoles().add(role);
        return surepo.save(sUser);
    }

    public SubjectUser addUserToRole(Long userId, Long subjectId, Long roleId){
        SubjectUser sUser = surepo.findByUserIdAndSubjectId(userId, subjectId).orElse(null);
        SubjectRole role = srrepo.findById(roleId).orElse(null);

        if (sUser == null || role == null)
            return null;
        sUser.getRoles().add(role);
        return surepo.save(sUser);
    }

    public SubjectUser removeUserFromRole(Long userId, Long subjectId, String roleName)
    {
        SubjectUser sUser = surepo.findByUserIdAndSubjectId(userId, subjectId).orElse(null);
        if (sUser == null || roleName == null)
            return null;

        sUser.removeRole(roleName);
        return surepo.save(sUser);
    }
}
