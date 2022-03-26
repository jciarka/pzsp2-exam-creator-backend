package com.PZSP2.PFIMJ.projections;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubjectProjection {
    private Long id;
    private String name;
    private String description;

    public SubjectProjection(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
