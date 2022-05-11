package com.PZSP2.PFIMJ.models.tests;

import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.db.entities.TestExercise;
import lombok.*;
import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintableTest {
    private String subjectName;
    private String title;
    private String description;
    private List<Exercise> exercises;

    public PrintableTest(Test test)
    {
        subjectName = test.getSubject().getName();
        title = test.getTitle();
        description = test.getDescription();
        Set<TestExercise> exerciseList = test.getExercises();

        exercises = exerciseList
            .stream()
            .sorted(Comparator.comparingInt(TestExercise::getExerciseNumber))
            .map(x -> x.getExercise())
            .collect(Collectors.toList());
    }
}
