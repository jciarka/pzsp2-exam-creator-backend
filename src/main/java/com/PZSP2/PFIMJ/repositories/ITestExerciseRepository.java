package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.TestExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITestExerciseRepository extends JpaRepository<TestExercise,Long> {
}
