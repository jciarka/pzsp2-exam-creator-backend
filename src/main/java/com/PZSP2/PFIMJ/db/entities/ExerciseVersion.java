package com.PZSP2.PFIMJ.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseVersion {
    String text;
    List<Answer> answers;
}
