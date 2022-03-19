package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.SubjectRole;
import com.PZSP2.PFIMJ.db.entities.SubjectUser;
import com.PZSP2.PFIMJ.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubjectUsersRepository extends JpaRepository<SubjectUser, Long>{
    @Query(value = "SELECT su FROM SubjectUser su WHERE su.id.userId = :userId and su.id.subjectId = :subjectId")
    public Optional<SubjectUser> findByUserIdAndSubjectId(@Param("userId") Long userId, @Param("subjectId") Long subjectId);

    @Query(value = "SELECT su FROM SubjectUser su WHERE su.id.userId = :userId")
    public List<SubjectUser> findAllByUserId(@Param("userId") Long userId);
}