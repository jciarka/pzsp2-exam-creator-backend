package com.PZSP2.PFIMJ.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoolModel {
    private Long id;
    private String name;
    private String description;
    private Long subjectId;

    public PoolModel(Long id, String name, String description, Long subjectId) {
        this.name = name;
        this.description = description;
        this.subjectId = subjectId;
        this.id = id;
    }
}
