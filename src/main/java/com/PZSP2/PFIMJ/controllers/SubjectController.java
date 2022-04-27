package com.PZSP2.PFIMJ.controllers;


import com.PZSP2.PFIMJ.projections.SubjectProjection;
import com.PZSP2.PFIMJ.services.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
