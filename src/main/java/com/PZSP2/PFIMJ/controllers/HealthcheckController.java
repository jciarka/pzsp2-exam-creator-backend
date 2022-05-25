package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.models.EmptyResponse;
import com.PZSP2.PFIMJ.models.subject.ParticipantModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/healthcheck")
public class HealthcheckController extends ControllerBase {
    @GetMapping
    public ResponseEntity<EmptyResponse> getSubjectParticipants(){
        return new ResponseEntity<>(new EmptyResponse(true), HttpStatus.OK);
    }
}
