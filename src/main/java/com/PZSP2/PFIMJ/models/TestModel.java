package com.PZSP2.PFIMJ.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TestModel {
    private Long id;
    private String title;
    private String description;
    private Long subjectId;

    public TestModel(Long id, String title, String description, Long subjectId) {
        this.title = title;
        this.description = description;
        this.subjectId = subjectId;
        this.id = id;
    }
}
