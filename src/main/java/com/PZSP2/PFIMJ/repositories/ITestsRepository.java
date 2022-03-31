package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.Test;

import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.projections.TestProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITestsRepository extends JpaRepository<Test, Long> {
    public Optional<Test> findTestByTitle(String name);

    @Query("SELECT new com.PZSP2.PFIMJ.projections.TestProjection(t.id,t.title,t.description,t.subject,t.exercises) FROM Test t JOIN t.subject s on s.id = :subjectId")
    public List<TestProjection> findBySubjectId(long subjectId);
}
