package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.db.entities.TestExercise;
import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.models.TestModel;
import com.PZSP2.PFIMJ.models.tests.PrintableTest;
import com.PZSP2.PFIMJ.projections.SubjectProjection;
import com.PZSP2.PFIMJ.projections.TestProjection;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import com.PZSP2.PFIMJ.repositories.ITestExerciseRepository;
import com.PZSP2.PFIMJ.repositories.ITestsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private ITestsRepository trepo;
    @Autowired
    private ITestExerciseRepository terepo;
    @Autowired
    private ISubjectsRepository sure;
    public PrintableTest getPrintableTest(long testId) {
        Test test = trepo.findByIdWithSubjectAndTests(testId).orElse(null);
        return test != null ? new PrintableTest(test) : null;
    }
    
    public List<Test> getSubjectTests(long subjectId){
        List<Test> subjectTests = trepo.findBySubjectId(subjectId);
        return subjectTests;
    }

    public Test addTest(TestModel testModel) {
        Test test = new Test();
        test.setDescription(testModel.getDescription());
        test.setTitle(testModel.getTitle());
        Subject subject = sure.findById(testModel.getSubjectId()).orElse(null);
        test.setSubject(subject);
        return trepo.save(test);
    }

    public boolean deleteTest(Long id){
        Optional<Test> toDelete = trepo.findById(id);
        if(!toDelete.isEmpty()){
            List<TestExercise> Exercises = terepo.findByTestId(id);
            for (TestExercise exercise : Exercises) {
                terepo.delete(exercise);
            };
            trepo.delete(toDelete.get());
            return true;
        }
        return false;
    }

    public boolean renameTest(TestModel request, Long testId) {
        Optional<Test> testToUpdate = trepo.findById(testId);
        if (!testToUpdate.isEmpty()){
            Test testToUpdateClass = testToUpdate.get();
            testToUpdateClass.setTitle(request.getTitle());
            trepo.save(testToUpdateClass);
            return true;
        }
        return false;
    }
    

    public boolean changeDescriptionPool(TestModel request, Long testId) {
        Optional<Test> testToUpdate = trepo.findById(testId);
        if (!testToUpdate.isEmpty()){
            Test testToUpdateClass = testToUpdate.get();
            testToUpdateClass.setDescription(request.getDescription());
            trepo.save(testToUpdateClass);
            return true;
        }
        return false;
    }

    public List<Test> getTestsByIdAndTitleLike(long subjectId, String title) {
        List<Test> subjectTests = trepo.findTestBySubjectIdAndTitleLike(subjectId, "%" + title + "%");
        return subjectTests;
    }
}
