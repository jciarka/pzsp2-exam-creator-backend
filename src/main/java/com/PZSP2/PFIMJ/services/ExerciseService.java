package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.ExerciseVersion;
import com.PZSP2.PFIMJ.db.entities.Pool;
import com.PZSP2.PFIMJ.models.ExerciseModel;
import com.PZSP2.PFIMJ.repositories.IExercisesRepository;
import com.PZSP2.PFIMJ.repositories.IPoolsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private IExercisesRepository exre;
    @Autowired
    private IPoolsRepository pore;

    public List<ExerciseModel> getBy(Long poolId){
        List<ExerciseModel> exerciseList = exre.findByPoolId(poolId);
        return exerciseList;
    }

    public List<ExerciseModel> getByTittleContaining(String phrase){
        List<ExerciseModel> exerciseModelList = exre.findByTitleContaining(phrase);
        return exerciseModelList;
    }

    public Exercise createExercise(ExerciseModel exerciseModel){
        Pool poolAssign = pore.findById(exerciseModel.getPoolId()).orElse(null);
        if (poolAssign==null){
            return null;
        }
        Exercise exercise = new Exercise();
        exercise.setTitle(exerciseModel.getTitle());
        exercise.setPoints(exerciseModel.getPoints());
        exercise.setVersions(exerciseModel.getVersions());
        exercise.setType(exerciseModel.getType());
        exercise.setPool(poolAssign);
        return exre.save(exercise);
    }

    public boolean deleteExercise(Long id){
        Optional<Exercise> toDelete = exre.findById(id);
        if(!toDelete.isEmpty()) {
            exre.delete(toDelete.get());
            return true;
        }
        return false;
    }
    public boolean changeTitle(Long id,String tittle){
        Exercise toEdit = exre.findById(id).orElse(null);
        if(toEdit!=null){
            toEdit.setTitle(tittle);
            exre.save(toEdit);
            return true;
        }
        return false;
    }

    public boolean changeType(Long id,String type){
        Exercise toEdit = exre.findById(id).orElse(null);
        if(toEdit!=null){
            toEdit.setType(type);
            exre.save(toEdit);
            return true;
        }
        return false;
    }

    public boolean changePoints(Long id,Integer points){
        Exercise toEdit = exre.findById(id).orElse(null);
        if(toEdit!=null){
            toEdit.setPoints(points);
            exre.save(toEdit);
            return true;
        }
        return false;
    }

    public boolean changeExerciseVersion(Long id, List<ExerciseVersion> versions){
        Optional<Exercise> toEdit = exre.findById(id);
        if(!toEdit.isEmpty()){
            Exercise exercise = toEdit.get();
            exercise.setVersions(versions);
            exre.save(exercise);
            return true;
        }
        return false;
    }
}
