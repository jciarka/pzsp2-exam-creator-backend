package com.PZSP2.PFIMJ.controllers;
import java.util.List;

import com.PZSP2.PFIMJ.models.TestModel;
import com.PZSP2.PFIMJ.projections.TestProjection;
import com.PZSP2.PFIMJ.services.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path="api/tests")
public class TestController {
    
    private final TestService testService;

    @Autowired
    public TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping(value="{subjectId}")
    public List<TestProjection> getSubjectTests(@PathVariable("subjectId") long subjectId){
        return this.testService.getSubjectTests(subjectId);
    }

    @PostMapping(value="/add",consumes="application/json")
    public ResponseEntity addTest(@RequestBody TestModel request){
        testService.addTest(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
