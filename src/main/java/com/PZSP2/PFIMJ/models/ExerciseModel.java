package com.PZSP2.PFIMJ.models;

import com.PZSP2.PFIMJ.db.entities.ExerciseVersion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ExerciseModel {
    private Long id;
    private String title;
    private String type;
    private Integer points;
    private Long poolId;
    private List<ExerciseVersion> versions;

    public ExerciseModel(Long id, String title, String type, Integer points, Long poolId, List<ExerciseVersion> versions) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.points = points;
        this.poolId = poolId;
        this.versions = versions;
    }
}
