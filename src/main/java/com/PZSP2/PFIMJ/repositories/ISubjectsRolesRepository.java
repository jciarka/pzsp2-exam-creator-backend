package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Role;
import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.SubjectRole;
import com.PZSP2.PFIMJ.db.entities.SubjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ISubjectsRolesRepository extends JpaRepository<SubjectRole, Long> {
    @Query(value = "SELECT r FROM SubjectRole r LEFT JOIN FETCH r.subjectUsers su WHERE r.name = :name")
    public Optional<SubjectRole> findByName(@Param("name") String Name);

    @Query(value = "SELECT r.name FROM SubjectRole r JOIN r.subjectUsers su WHERE su.id.userId = :userId and su.id.subjectId = :subjectId")
    public List<String> findByUserIdAndSubjectId(@Param("userId") Long userId, @Param("subjectId") Long subjectId);
}
