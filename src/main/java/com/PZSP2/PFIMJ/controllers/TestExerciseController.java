package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.db.entities.TestExercise;
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
        TestExercise testExercise = testExerciseService.addExerciseToTest(exerciseId,testId);
        if (testExercise==null){
            return ResponseEntity.ok().body("Exercise not assigned to test");
        }
        return ResponseEntity.ok().body("Exercise assigned to test");
    }
    @PostMapping(value="/add/{exerciseId}/{testId}/{nr}")
    public ResponseEntity addExerciseToTest(@PathVariable("exerciseId") Long exerciseId, @PathVariable("testId") Long testId, @PathVariable("nr") Integer nr ){
        TestExercise testExercise = testExerciseService.addExerciseToTest(exerciseId,testId,nr);
        if (testExercise==null){
            return ResponseEntity.ok().body("Exercise not assigned to test");
        }
        return ResponseEntity.ok().body("Exercise assigned to test");
    }
    
    @PostMapping(value="/add/{testId}")
    public ResponseEntity addExercisesToTest(@PathVariable("testId") Long testId, @RequestBody List<Long> exercises){
        testExerciseService.addExercisesToTest(testId,exercises);
        return ResponseEntity.ok().body("Exercises assigned to test");
    }

    @DeleteMapping(value="/delete/{reference_id}")
    public ResponseEntity deleteExerciseFromTest(@PathVariable("reference_id") Long reference_id){
        boolean isDeleted = testExerciseService.deleteExerciseFromTest(reference_id);
        if (isDeleted){
            return ResponseEntity.ok().body("Exercise deleted from test");
        }
        return ResponseEntity.badRequest().body("Exercise to delete not found");
    }
    @DeleteMapping(value="/delete/")
    public ResponseEntity deleteExerciseFromTest(@RequestBody List<Long> references){
        boolean isDeleted = testExerciseService.deleteExercisesFromTest(references);
        if (isDeleted){
            return ResponseEntity.ok().body("Exercises deleted from test");
        }
        return ResponseEntity.badRequest().body("Exercises to delete not found");
    }
    @DeleteMapping(value="/delete/{exerciseId}/{testId}")
    public ResponseEntity deleteExerciseFromTest(@PathVariable("exerciseId") Long exerciseId, @PathVariable("testId") Long testId){
        boolean isDeleted = testExerciseService.deleteExerciseFromTest(exerciseId,testId);
        if (isDeleted){
            return ResponseEntity.ok().body("Exercise deleted from test");
        }
        return ResponseEntity.badRequest().body("Exercise to delete not found");
    }
}
