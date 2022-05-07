package com.PZSP2.PFIMJ.controllers;



import com.PZSP2.PFIMJ.models.subject.ParticipantModel;
import com.PZSP2.PFIMJ.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/participants")
public class ParticipantController extends ControllerBase {


    private final UsersService usersService;

    @Autowired
    public ParticipantController(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping(value="{subjectId}")
    public List<ParticipantModel> getSubjectParticipants(@PathVariable("subjectId") long subjectId){
        return this.usersService.getSubjectParticipants(subjectId);
    }

    @GetMapping(value="{subjectId}/myself")
    public ParticipantModel getMyselfAsSubjectParticipants(@PathVariable("subjectId") long subjectId) {
        Long myId = getAuthenticatedUser().getId();
        return this.usersService.getSubjectUserParticipant(subjectId, myId);
    }

    @GetMapping(value="{subjectId}/{userId}")
    public ParticipantModel getSubjectParticipants(@PathVariable("subjectId") long subjectId, @PathVariable("userId") long userId){
        return this.usersService.getSubjectUserParticipant(subjectId, userId);
    }
}