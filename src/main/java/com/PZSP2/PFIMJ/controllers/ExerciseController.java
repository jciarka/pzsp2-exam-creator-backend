package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.models.ExerciseModel;
import com.PZSP2.PFIMJ.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController extends ControllerBase {

    private ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping(value="/pool/{PoolId}")
    public List<ExerciseModel> getExerciseFromPool(@PathVariable("PoolId") Long poolId){
        return exerciseService.getBy(poolId);
    }
    @GetMapping(value="/test/{testId}")
    public List<ExerciseModel> getExerciseFromTest(@PathVariable("testId") Long testId){
        return exerciseService.getExercisesFrom(testId);
    }

    @GetMapping(value="/titleContent")
    public List<ExerciseModel> getExerciseByTitleContent(@RequestBody String phrase){
        return exerciseService.getByTittleContaining(phrase);
    }
    @GetMapping(value="/versionsContent")
    public List<Exercise> getExerciseByVersionsContent(@RequestBody String phrase){
        return exerciseService.getByVersionsContaining(phrase);
    }

    @PostMapping(value="/add", consumes="application/json")
    public ResponseEntity createExercise(@RequestBody ExerciseModel exerciseModel){
//        if (!isAuthenticated()){
//            return Response.GetNegative((SubjectProjection)null, HttpStatus.UNAUTHORIZED);
//        }
        System.out.println(exerciseModel.toString());
        Exercise exercise = exerciseService.createExercise(exerciseModel);
        if(exercise==null){
            return ResponseEntity.badRequest().body("Exercise not created");
        }
        return ResponseEntity.ok().body("Exercise created");
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity deleteExercise(@PathVariable("id") Long id){
//        if (!isAuthenticated()){
//            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.UNAUTHORIZED);
//        }
        boolean isDeleted = exerciseService.deleteExercise(id);
        if (isDeleted){
            return ResponseEntity.ok().body("Exercise deleted");
        }
        return ResponseEntity.badRequest().body("Exercise to delete not found");
    }

    @PutMapping(value = "/{exerciseId}/title", consumes = "application/json")
    public ResponseEntity changeTittle(@PathVariable("exerciseId") Long id, @RequestBody ExerciseModel exercise) {
//        if (!isAuthenticated()){
//            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.UNAUTHORIZED);
//        }
        boolean isUpdated = exerciseService.changeTitle(id, exercise.getTitle());
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{exerciseId}/type", consumes = "application/json")
    public ResponseEntity changeType(@PathVariable("exerciseId") Long id, @RequestBody ExerciseModel exercise) {
//        if (!isAuthenticated()){
//            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.UNAUTHORIZED);
//        }
        boolean isUpdated = exerciseService.changeType(id, exercise.getType());
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{exerciseId}/points", consumes = "application/json")
    public ResponseEntity changePoints(@PathVariable("exerciseId") Long id, @RequestBody ExerciseModel exercise) {
//        if (!isAuthenticated()){
//            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.UNAUTHORIZED);
//        }
        boolean isUpdated = exerciseService.changePoints(id, exercise.getPoints());
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{exerciseId}/versions", consumes = "application/json")
    public ResponseEntity changeVersions(@PathVariable("exerciseId") Long id, @RequestBody ExerciseModel exercise) {
//        if (!isAuthenticated()){
//            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.UNAUTHORIZED);
//        }
        boolean isUpdated = exerciseService.changeExerciseVersion(id, exercise.getVersions());
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}