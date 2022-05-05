package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.models.ExerciseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IExercisesRepository extends JpaRepository<Exercise,Long> {

    @Query(value="SELECT new com.PZSP2.PFIMJ.models.ExerciseModel(e.id,e.title,e.type,e.points,e.pool.id,e.versions) FROM Exercise e WHERE e.pool.id =:poolId")
    public List<ExerciseModel> findByPoolId(@Param("poolId") Long poolId);


    public List<ExerciseModel> findByTitleContaining(String phrase);

}
