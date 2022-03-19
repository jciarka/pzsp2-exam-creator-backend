package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubjectsRepository extends JpaRepository<Subject, Long> {
    public Optional<Subject> findSubjectByName(String name);

    @Query("SELECT s FROM Subject s JOIN s.subjectUsers su on su.id.userId = :userId")
    public List<Subject> findByUserId(@Param("userId") Long userId);
}
