package com.PZSP2.PFIMJ.controllers;



import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.models.auth.UserModel;
import com.PZSP2.PFIMJ.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/participants")
public class ParticipantController {


    private final UsersService usersService;

    @Autowired
    public ParticipantController(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping(value="{subjectId}")
    public List<User> getSubjectParticipants(@PathVariable("subjectId") long subjectId){
        return this.usersService.getSubjectParticipants(subjectId);
    }
}