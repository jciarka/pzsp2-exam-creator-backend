package com.PZSP2.PFIMJ.projections;


import java.util.List;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.models.PoolModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectProjectionwithPools {
    private Long id;
    private String name;
    private String description;
    private List<PoolModel> pools;
}
