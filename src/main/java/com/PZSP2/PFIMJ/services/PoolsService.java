package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.Pool;
import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.models.ExerciseModel;
import com.PZSP2.PFIMJ.models.PoolModel;
import com.PZSP2.PFIMJ.repositories.IExercisesRepository;
import com.PZSP2.PFIMJ.repositories.IPoolsRepository;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoolsService {

    @Autowired
    IPoolsRepository pore;
    @Autowired
    ISubjectsRepository sure;
    @Autowired
    IExercisesRepository exre;

    public Pool addPool(PoolModel poolModel){
        Pool pool = new Pool();
        pool.setDescription(poolModel.getDescription());
        pool.setName(poolModel.getName());
        Subject subject = sure.findById(poolModel.getSubjectId()).orElse(null);
        pool.setSubject(subject);
        return pore.save(pool);
    }

    public boolean renamePool(PoolModel request, Long id){
        Optional<Pool> poolToUpdate = pore.findById(id);
        if (!poolToUpdate.isEmpty()){
            Pool poolToUpdateClass = poolToUpdate.get();
            poolToUpdateClass.setName(request.getName());
            pore.save(poolToUpdateClass);
            return true;
        }
        return false;
    }

    public boolean changeDescriptionPool(PoolModel request, Long id){
        Optional<Pool> poolToUpdate = pore.findById(id);
        if (!poolToUpdate.isEmpty()){
            Pool poolToUpdateClass = poolToUpdate.get();
            poolToUpdateClass.setDescription(request.getDescription());
            pore.save(poolToUpdateClass);
            return true;
        }
        return false;
    }

    public boolean deletePool(Long id){
        Optional<Pool> toDelete = pore.findById(id);
        if(!toDelete.isEmpty()){
            pore.delete(toDelete.get());
            return true;
        }
        return false;
    }

    public List<PoolModel> getSubjectPools(Long id){
        List<PoolModel> pools = pore.findBySubjectId(id);
        return pools;
    }

    public boolean createPoolsCopy(Long subjectId,List<Long> pools){
        Pool poolToCopy = null;
        if (pools.isEmpty()){
            return false;
        }
        for (Long poolId : pools){
            poolToCopy = pore.findById(poolId).orElse(null);
            if (poolToCopy!=null){
                PoolModel p = new PoolModel(null,poolToCopy.getName(),poolToCopy.getDescription(),subjectId);
                copyPoolsExercises(poolToCopy,addPool(p));
            }
        }
        return true;
    }
    public boolean copyPoolsExercises(Pool pool,Pool newPool){
        List<ExerciseModel> exerciseModelList = exre.findByPoolId(pool.getId());
        for (ExerciseModel e : exerciseModelList){
            Exercise exercise = new Exercise();
            exercise.setTitle(e.getTitle());
            exercise.setPoints(e.getPoints());
            exercise.setVersions(e.getVersions());
            exercise.setType(e.getType());
            exercise.setPool(newPool);
            exre.save(exercise);
        }
        return true;
    }
}
