package com.PZSP2.PFIMJ.db.entities;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseVersion {
    String text;
    List<Answer> answers;
}
