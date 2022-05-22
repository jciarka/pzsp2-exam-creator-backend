package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.services.TestExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/testexercise")
public class TestExerciseController {

    private TestExerciseService testExerciseService;

    @Autowired
    public TestExerciseController(TestExerciseService testExerciseService) {
        this.testExerciseService = testExerciseService;
    }

    @PostMapping(value="/add/{exerciseId}/{testId}")
    public ResponseEntity addExerciseToTest(@PathVariable("exerciseId") Long exerciseId, @PathVariable("testId") Long testId ){
        testExerciseService.addExerciseToTest(exerciseId,testId);
        return ResponseEntity.ok().body("Exercise assigned to test");
    }
    @PostMapping(value="/add/{exerciseId}/{testId}/{nr}")
    public ResponseEntity addExerciseToTest(@PathVariable("exerciseId") Long exerciseId, @PathVariable("testId") Long testId, @PathVariable("nr") Integer nr ){
        testExerciseService.addExerciseToTest(exerciseId,testId,nr);
        return ResponseEntity.ok().body("Exercise assigned to test");
    }
    
    @PostMapping(value="/add/{testId}")
    public ResponseEntity addExercisesToTest(@PathVariable("testId") Long testId, @RequestBody List<Long> exercises){
        testExerciseService.addExercisesToTest(testId,exercises);
        return ResponseEntity.ok().body("Exercises assigned to test");
    }
}
