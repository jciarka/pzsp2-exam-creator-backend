package com.PZSP2.PFIMJ.controllers;


import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.models.Response;
import com.PZSP2.PFIMJ.models.auth.UserModel;
import com.PZSP2.PFIMJ.projections.SubjectProjection;
import com.PZSP2.PFIMJ.projections.SubjectProjectionwithPools;
import com.PZSP2.PFIMJ.services.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path="api/subjects")
public class SubjectController extends ControllerBase {
    private final SubjectsService subjectsService;

    @Autowired
    public SubjectController(SubjectsService subjectsService) {
        this.subjectsService = subjectsService;
    }

    @GetMapping(value = "{userId}")
    public List<SubjectProjectionwithPools> getUserSubjects(@PathVariable("userId") long userId) {
        return this.subjectsService.getUserSubjects(userId);
    }

    @PostMapping
    public ResponseEntity<Response<SubjectProjection>> getUserSubjects(@RequestBody Subject subject) {
        if (!isAuthenticated()){
            return Response.GetNegative((SubjectProjection)null, HttpStatus.UNAUTHORIZED);
        }

        Long userId = getAuthenticatedUser().getId();
        Subject newSubject = subjectsService.add(subject, userId);

        if (subject == null) {
            return Response.GetNegative((SubjectProjection)null, HttpStatus.BAD_REQUEST,
                    Arrays.asList("Colud not create subject with given data"));
        }

        return Response.GetPositive(new SubjectProjection(newSubject), HttpStatus.CREATED);
    }
}