package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.models.ExerciseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.PZSP2.PFIMJ.core.tests.ExerciseContentConverter;
import java.util.List;


@Repository
public interface IExercisesRepository extends JpaRepository<Exercise,Long> {

    @Query(value="SELECT new com.PZSP2.PFIMJ.models.ExerciseModel(e.id,e.title,e.type,e.points,e.pool.id,e.versions) FROM Exercise e WHERE e.pool.id =:poolId")
    public List<ExerciseModel> findByPoolId(@Param("poolId") Long poolId);
    
    @Query(value="SELECT new com.PZSP2.PFIMJ.models.ExerciseModel(e.id,e.title,e.type,e.points,e.pool.id,e.versions) FROM Exercise e JOIN TestExercise t ON e.id=t.exercise.id WHERE t.test.id=:testId ORDER BY t.exerciseNumber")
    public List<ExerciseModel> findByTestId(@Param("testId") Long testId);

    public List<ExerciseModel> findByTitleContaining(String phrase);

//    @Query(value="SELECT new com.PZSP2.PFIMJ.models.ExerciseModel(e.id,e.title,e.type,e.points,e.pool.id,e.versions) FROM Exercise e " +
//            "WHERE com.PZSP2.PFIMJ.core.tests.ExerciseContentConverter.convertToDatabaseColumn(e.versions) LIKE :phrase")
    @Query(value="SELECT * FROM exercises WHERE Lower(versions) like %:phrase%",nativeQuery = true)
    public List<Exercise> findByVersionsContaining(@Param("phrase")String phrase);
}
