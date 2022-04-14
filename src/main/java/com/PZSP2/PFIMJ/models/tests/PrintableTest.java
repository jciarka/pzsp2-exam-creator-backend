package com.PZSP2.PFIMJ.models.tests;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrintableTest {
    private String title;
    private String description;
    private List<Exercise> exercises;
}
