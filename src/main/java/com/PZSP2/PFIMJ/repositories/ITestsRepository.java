package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Subject;

import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.models.TestModel;
import com.PZSP2.PFIMJ.projections.TestProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITestsRepository extends JpaRepository<Test, Long> {
    public List<Test> findTestBySubjectIdAndTitleLike(long subjectId, String name);

//    @Query("SELECT new com.PZSP2.PFIMJ.projections.TestProjection(t.id,t.title,t.description,t.subject,t.exercises) FROM Test t JOIN t.subject s on s.id = :subjectId")
    public List<Test> findBySubjectId(long subjectId);

    @Query(
            "SELECT t " +
            "FROM Test t JOIN FETCH t.subject s JOIN FETCH t.exercises tex JOIN FETCH tex.exercise " +
            "WHERE t.id = :testId"
    )
    public Optional<Test> findByIdWithSubjectAndTests(long testId);
}
