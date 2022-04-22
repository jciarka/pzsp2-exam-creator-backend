package com.PZSP2.PFIMJ.repositories;

import com.PZSP2.PFIMJ.db.entities.Pool;
import com.PZSP2.PFIMJ.models.PoolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPoolsRepository extends JpaRepository<Pool,Long> {
    public Optional<Pool> findById(Long id);

    @Query("SELECT new com.PZSP2.PFIMJ.models.PoolModel(p.id,p.name,p.description,p.subject.id) FROM Pool p WHERE p.subject.id=:id")
    public List<PoolModel> findBySubjectId(Long id);
}
