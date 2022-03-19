package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.Test;

import com.PZSP2.PFIMJ.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITestsRepository extends JpaRepository<Test, Long> {
    public Optional<Test> findTestByTitle(String name);
}
