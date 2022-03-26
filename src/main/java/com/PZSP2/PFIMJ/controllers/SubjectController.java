package com.PZSP2.PFIMJ.controllers;


import com.PZSP2.PFIMJ.projections.SubjectProjection;
import com.PZSP2.PFIMJ.services.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/subjects")
public class SubjectController {


    private final SubjectsService subjectsService;

    @Autowired
    public SubjectController(SubjectsService subjectsService){
        this.subjectsService = subjectsService;
    }

    @GetMapping(value="{userId}")
    public List<SubjectProjection> getUserSubjects(@PathVariable("userId") long userId){
        return this.subjectsService.getUserSubjects(userId);
    }

}
