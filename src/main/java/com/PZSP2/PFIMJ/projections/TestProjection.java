package com.PZSP2.PFIMJ.projections;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.TestExercise;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TestProjection {
    private Long id;
    private String title;
    private String description;
    private Subject subject;
    private TestExercise exercises;

    public TestProjection(Long id, String title, String description, Subject subject, TestExercise exercises) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.exercises = exercises;
    }
}
