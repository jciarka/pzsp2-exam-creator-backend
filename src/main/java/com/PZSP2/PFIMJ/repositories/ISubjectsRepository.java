package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.User;
import com.PZSP2.PFIMJ.projections.SubjectProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubjectsRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s LEFT JOIN FETCH s.subjectUsers su WHERE s.id = :id")
    public Optional<Subject> findById(@Param("id") Long id);

    public Optional<Subject> findSubjectByName(String name);

    @Query("SELECT new com.PZSP2.PFIMJ.projections.SubjectProjection(s.id,s.name,s.description) FROM Subject s JOIN s.subjectUsers su on su.id.userId = :userId")
    public List<SubjectProjection> findByUserId(@Param("userId") Long userId);
}
