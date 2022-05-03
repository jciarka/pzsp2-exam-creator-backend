package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.models.TestModel;
import com.PZSP2.PFIMJ.projections.SubjectProjection;
import com.PZSP2.PFIMJ.projections.TestProjection;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import com.PZSP2.PFIMJ.repositories.ITestsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private ITestsRepository trepo;
    @Autowired
    private ISubjectsRepository sure;
    
    public List<TestProjection> getSubjectTests(long subjectId){
        List<TestProjection> subjectTests = trepo.findBySubjectId(subjectId);
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

}
