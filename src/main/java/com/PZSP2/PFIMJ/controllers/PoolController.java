package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.db.entities.Pool;
import com.PZSP2.PFIMJ.models.PoolModel;
import com.PZSP2.PFIMJ.services.PoolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pool")
public class PoolController {

    private final PoolsService poolsService;

    @Autowired
    public PoolController(PoolsService poolsService) {
        this.poolsService = poolsService;
    }

    @PostMapping(value="/add",consumes="application/json")
    public ResponseEntity addPool(@RequestBody PoolModel request){
        poolsService.addPool(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/pools/{subject_id}")
    public List<PoolModel> getPools(@PathVariable Long subject_id){
        List<PoolModel> pools = poolsService.getSubjectPools(subject_id);
        return pools;
    }
    @PutMapping(value="/rename/{poolId}",consumes="application/json")
    public ResponseEntity updateNamePool(@PathVariable Long poolId, @RequestBody PoolModel request){
        boolean isUpdated = poolsService.renamePool(request,poolId);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value="/description/{poolId}",consumes="application/json")
    public ResponseEntity updateDescriptionPool(@PathVariable Long poolId, @RequestBody PoolModel request){
        boolean isUpdated = poolsService.changeDescriptionPool(request,poolId);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value="/delete/{poolId}")
    public ResponseEntity deletePool(@PathVariable Long poolId){
        boolean isDeleted = poolsService.deletePool(poolId);
        if(!isDeleted){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
