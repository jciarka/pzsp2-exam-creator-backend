package com.PZSP2.PFIMJ.db.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseVersion {
    String text;
    List<Answer> answers;
}
