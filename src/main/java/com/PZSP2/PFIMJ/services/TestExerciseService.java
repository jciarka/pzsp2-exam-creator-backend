package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.db.entities.TestExercise;
import com.PZSP2.PFIMJ.repositories.IExercisesRepository;
import com.PZSP2.PFIMJ.repositories.ITestExerciseRepository;
import com.PZSP2.PFIMJ.repositories.ITestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestExerciseService {

    @Autowired
    private ITestExerciseRepository teexre;

    @Autowired
    private ITestsRepository tere;

    @Autowired
    private IExercisesRepository exre;

    //nieskonczone
    public TestExercise addExerciseToTest(Long exerciseId, Long testId, Integer exerciseNumber){
        Exercise exercise = exre.findById(exerciseId).orElse(null);
        Test test = tere.findById(testId).orElse(null);

        if (exercise == null || test == null){
            return null;
        }

        TestExercise testExercise = new TestExercise();
        return teexre.save(testExercise);
    }
}
