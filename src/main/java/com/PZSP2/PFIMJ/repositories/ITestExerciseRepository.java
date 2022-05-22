package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.db.entities.TestExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITestExerciseRepository extends JpaRepository<TestExercise,Long> {

    public List<TestExercise> findByTestId(long testId);

    @Query("SELECT te FROM TestExercise te WHERE te.exercise.id=:exerciseId AND te.test.id=:testId")
    public List<TestExercise> findByTestIdAndExerciseId(long testId,long exerciseId);

    @Query("SELECT max(tex.exerciseNumber) FROM TestExercise tex WHERE tex.test.id = :testId")
    public Optional<Integer> findLastExerciseNumberInTest(long testId);

}
