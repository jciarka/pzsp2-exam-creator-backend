package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.db.entities.SubjectUser;
import com.PZSP2.PFIMJ.projections.SubjectUserWithRoles;
import com.PZSP2.PFIMJ.services.SubjectUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api/subjectuser")
public class SubjectUserController {

    public final SubjectUsersService subjectUsersService;

    @Autowired
    public SubjectUserController(SubjectUsersService subjectUsersService) {
        this.subjectUsersService = subjectUsersService;
    }

    @PostMapping(value="/add",consumes="application/json")
    public SubjectUser addSubjectUser(@RequestBody SubjectUserWithRoles sur){
        return subjectUsersService.addUserToSubject2(sur.getUserId(),sur.getSubjectId(),sur.getSubjectRoles());
    }
}
