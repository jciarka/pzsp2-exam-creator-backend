package com.PZSP2.PFIMJ.db.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Exercise {
    String title;
    String type;
    List<ExerciseVersion> versions;
}
