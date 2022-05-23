package com.PZSP2.PFIMJ;

import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import com.PZSP2.PFIMJ.repositories.ITestExerciseRepository;
import com.PZSP2.PFIMJ.repositories.ITestsRepository;
import com.PZSP2.PFIMJ.services.TestService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.db.entities.TestExercise;
import com.PZSP2.PFIMJ.models.TestModel;

import org.assertj.core.error.future.Warning;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestServiceTests {
    @Mock
    private ITestsRepository trepo;
    @Mock
    private ITestExerciseRepository terepo;
    @Mock
    private ISubjectsRepository sure;
    @InjectMocks
    private TestService testservice;
    
    @org.junit.jupiter.api.Test
    void getSubjectTestsTest(){
        Test test = new Test();
        Subject subject = new Subject();
        test.setSubject(subject);
        when(trepo.findBySubjectId(test.getSubject().getId())).thenReturn(List.of(test));
        List<Test> subjectTests = testservice.getSubjectTests(subject.getId());
        assertEquals(subjectTests.get(0), test);
    }

    @org.junit.jupiter.api.Test
    void addTestTest(){
        Test test = new Test();
        Subject subject = new Subject();
        test.setSubject(subject);
        when(sure.findById(subject.getId())).thenReturn(Optional.of(subject));
        when(trepo.save(any())).then(returnsFirstArg());
        TestModel testModel = new TestModel(test.getId(), test.getTitle(), test.getDescription(), test.getSubject().getId());
        Test addedtest = testservice.addTest(testModel);
        assertEquals(addedtest.getId(), test.getId());
        assertEquals(addedtest.getDescription(), test.getDescription());
        assertEquals(addedtest.getSubject(), test.getSubject());
        assertEquals(addedtest.getTitle(), test.getTitle());
    }

    @org.junit.jupiter.api.Test
    void deleteTestTest(){
        Test test = new Test();
        List<TestExercise> excercises = new ArrayList<>();
        TestExercise testExercise = new TestExercise();
        excercises.add(testExercise);
        when(trepo.findById(test.getId())).thenReturn(Optional.of(test));
        when(terepo.findByTestId(test.getId())).thenReturn(excercises);
        boolean subjectTests = testservice.deleteTest(test.getId());
        verify(terepo).delete(any());
        verify(trepo).delete(any());
        assertTrue(subjectTests);
    }

    @org.junit.jupiter.api.Test
    void deleteWrongIdTestTest(){
        boolean subjectTestsWrong = testservice.deleteTest((long) 5);
        verify(terepo, times(0)).delete(any());
        verify(trepo, times(0)).delete(any());
        assertFalse(subjectTestsWrong);
    }

    @org.junit.jupiter.api.Test
    void renameTestTest(){
        Test test = new Test();
        TestModel testModel = new TestModel(test.getId(), test.getTitle(), test.getDescription(), test.getId());
        testModel.setTitle("newtitle");
        when(trepo.findById(test.getId())).thenReturn(Optional.of(test));
        boolean subjectTests = testservice.renameTest(testModel, test.getId());
        verify(trepo).save(any());
        assertTrue(subjectTests);
    }

    @org.junit.jupiter.api.Test
    void renameWrongTestTest(){
        Test test = new Test();
        TestModel testModel = new TestModel(test.getId(), test.getTitle(), test.getDescription(), test.getId());
        boolean subjectTests = testservice.renameTest(testModel, (long) 5);
        verify(trepo, times(0)).save(any());
        assertFalse(subjectTests);
    }

    @org.junit.jupiter.api.Test
    void changeDescriptionPoolTest(){
        Test test = new Test();
        TestModel testModel = new TestModel(test.getId(), test.getTitle(), test.getDescription(), test.getId());
        when(trepo.findById(test.getId())).thenReturn(Optional.of(test));
        boolean subjectTests = testservice.changeDescriptionPool(testModel, test.getId());
        verify(trepo).save(any());
        assertTrue(subjectTests);
    }

    @org.junit.jupiter.api.Test
    void changeWrongDescriptionPoolTest(){
        Test test = new Test();
        TestModel testModel = new TestModel(test.getId(), test.getTitle(), test.getDescription(), test.getId());
        boolean subjectTests = testservice.changeDescriptionPool(testModel, (long) 5);
        verify(trepo, times(0)).save(any());
        assertFalse(subjectTests);
    }

    @org.junit.jupiter.api.Test
    void getTestsByIdAndTitleLikeTest(){
        Test test = new Test();
        Subject subject = new Subject();
        test.setSubject(subject);
        when(trepo.findTestBySubjectIdAndTitleLike(subject.getId(), "%" + test.getTitle() + "%")).thenReturn(List.of(test));
        List<Test> subjectTests = testservice.getTestsByIdAndTitleLike(subject.getId(), test.getTitle());
        assertEquals(subjectTests.get(0), test);
    }

    @org.junit.jupiter.api.Test
    void getTestsByWrongIdAndTitleLikeTest(){
        Test test = new Test();
        Subject subject = new Subject();
        test.setSubject(subject);
        List<Test> subjectTests = testservice.getTestsByIdAndTitleLike((long) 5, test.getTitle());
        assertTrue(subjectTests.isEmpty());
    }
}
