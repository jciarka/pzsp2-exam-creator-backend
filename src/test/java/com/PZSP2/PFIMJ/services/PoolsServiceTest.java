package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.db.entities.Pool;
import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.models.ExerciseModel;
import com.PZSP2.PFIMJ.models.PoolModel;
import com.PZSP2.PFIMJ.repositories.IExercisesRepository;
import com.PZSP2.PFIMJ.repositories.IPoolsRepository;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PoolsServiceTest {

    @Mock
    private IPoolsRepository pore;
    @Mock
    private ISubjectsRepository sure;
    @Mock
    private IExercisesRepository exre;
    @InjectMocks
    private PoolsService poolsService;

    @Test
    void addPool() {
        Pool pool = new Pool();
        Subject subject = new Subject();
        pool.setSubject(subject);
        when(sure.findById(subject.getId())).thenReturn(Optional.of(subject));
        when(pore.save(any())).then(returnsFirstArg());
        PoolModel poolModel = new PoolModel(pool.getId(),pool.getName(),pool.getDescription(),pool.getSubject().getId());
        Pool addedPool = poolsService.addPool(poolModel);
        assertAll(
                () -> assertEquals(addedPool.getId(),poolModel.getId()),
                () -> assertEquals(addedPool.getDescription(),poolModel.getDescription()),
                () -> assertEquals(addedPool.getName(),poolModel.getName()),
                () -> assertEquals(addedPool.getSubject().getId(),poolModel.getSubjectId())
        );
    }

    @Test
    void renamePoolSuccess() {
        Pool pool = new Pool();
        PoolModel poolModel = new PoolModel(pool.getId(),pool.getName(),pool.getDescription(),1L);
        poolModel.setName("newName");
        when(pore.findById(pool.getId())).thenReturn(Optional.of(pool));
        when(pore.save(any())).then(returnsFirstArg());
        boolean isRenamed = poolsService.renamePool(poolModel,pool.getId());
        assertAll(
                () -> assertTrue(isRenamed),
                () -> assertEquals(pool.getName(),poolModel.getName())
        );
    }

    @Test
    void renamePoolFailed() {
        Pool pool = new Pool();
        PoolModel poolModel = new PoolModel(pool.getId(),pool.getName(),pool.getDescription(),1L);
        poolModel.setName("newName");
        boolean isRenamed = poolsService.renamePool(poolModel,2L);
        verify(pore,times(0)).save(any());
        assertFalse(isRenamed);
    }

    @Test
    void changeDescriptionPool() {
        Pool pool = new Pool();
        PoolModel poolModel = new PoolModel(pool.getId(),pool.getName(),pool.getDescription(),1L);
        poolModel.setDescription("newDescription");
        when(pore.findById(pool.getId())).thenReturn(Optional.of(pool));
        when(pore.save(any())).then(returnsFirstArg());
        boolean isDescriptionChanged = poolsService.changeDescriptionPool(poolModel,pool.getId());
        assertTrue(isDescriptionChanged);
    }

    @Test
    void changeDescriptionFailed() {
        Pool pool = new Pool();
        PoolModel poolModel = new PoolModel(pool.getId(),pool.getName(),pool.getDescription(),1L);
        poolModel.setDescription("newDescription");
        boolean isDescriptionChanged = poolsService.changeDescriptionPool(poolModel,2L);
        verify(pore,times(0)).save(any());
        assertFalse(isDescriptionChanged);
    }

    @Test
    void deletePool() {
        Pool pool = new Pool();
        when(pore.findById(pool.getId())).thenReturn(Optional.of(pool));
        boolean isDeleted = poolsService.deletePool(pool.getId());
        verify(pore).delete(any());
        assertTrue(isDeleted);
    }

    @Test
    void deletePoolWhichNotExists() {
        boolean isDeleted = poolsService.deletePool(5L);
        verify(pore,times(0)).delete(any());
        assertFalse(isDeleted);
    }

    @Test
    void getSubjectPools() {
        Pool pool = new Pool();
        Subject subject = new Subject();
        pool.setSubject(subject);
        PoolModel poolModel = new PoolModel(pool.getId(),pool.getName(),pool.getDescription(),pool.getSubject().getId());
        when(pore.findBySubjectId(subject.getId())).thenReturn(List.of(poolModel));
        List<PoolModel> pools = poolsService.getSubjectPools(subject.getId());
        assertEquals(pools.get(0),poolModel);
    }

    @Test
    void createPoolsCopy() {
        Pool pool = new Pool();
        Subject subject = new Subject();
        List<Long> poolsId = List.of(pool.getId());
        when(pore.findById(poolsId.get(0))).thenReturn(Optional.of(pool));
        boolean isCopied = poolsService.createPoolsCopy(subject.getId(),poolsId);
        assertTrue(isCopied);
    }

    @Test
    void copyPoolsExercises() {
        Pool pool = new Pool();
        Pool newPool = new Pool();
        List<ExerciseModel> exercises= new ArrayList<>();
        when(exre.findByPoolId(pool.getId())).thenReturn(exercises);
        boolean isCopied = poolsService.copyPoolsExercises(pool,newPool);
        verify(exre,times(exercises.size())).save(any());
        assertEquals(pool.getExercises().size(),newPool.getExercises().size());
        assertTrue(isCopied);
    }
}