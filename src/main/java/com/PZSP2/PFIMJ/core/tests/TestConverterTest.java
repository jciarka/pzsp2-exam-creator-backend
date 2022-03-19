package com.PZSP2.PFIMJ.core.tests;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.ExerciseVersion;
import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import com.PZSP2.PFIMJ.repositories.ITestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TestConverterTest {
    @Autowired
    private ITestsRepository trepo;

    @Autowired
    private ISubjectsRepository srepo;

//    @PostConstruct
//    public void init() {
//
//        Subject subject = new Subject();
//        subject.setName("PZSP");
//        subject.setDescription("12345");
//        srepo.save(subject);
//
//        ExerciseVersion version1 = new ExerciseVersion();
//        version1.setText("Oto jest pytanie");
//        Exercise exercise = new Exercise();
//
//        List<ExerciseVersion> versions = new ArrayList<>();
//        versions.add(version1);
//
//        exercise.setVersions(versions);
//        exercise.setTitle("Zadanie z geometrii");
//        exercise.setType("PLAIN_TEXT");
//
//        Test test = new Test();
//        test.setTitle("Test 1");
//        test.setDescription("Kol. 1");
//
//        List<Exercise> exerciseList = new ArrayList<>();
//        exerciseList.add(exercise);
//
//        test.setExercises(exerciseList);
//
//        // save test
//        Subject addedSubject = srepo.findSubjectByName(subject.getName()).orElse(null);
//
//        test.setSubject(addedSubject);
//        Set<Test> tests = new HashSet<>();
//        tests.add(test);
//        addedSubject.setTests(tests);
//
//        trepo.save(test);
//
//        Test addedTest = trepo.findTestByTitle(test.getTitle()).orElse(null);
//        System.out.println(addedTest.getId());
//    }
}
