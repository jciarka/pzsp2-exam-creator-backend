package com.PZSP2.PFIMJ.db.entities;

import com.PZSP2.PFIMJ.core.tests.ExerciseContentConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "ExerciseTestReferences",
        indexes = {
                @Index(columnList = "exercise_id", name = "ex_reference_to_exercise_ix"),
                @Index(columnList = "test_id", name = "ex_reference_to_test_ix")
        }
)
public class TestExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_exercise_generator")
    @Column(name = "reference_id", columnDefinition = "NUMBER(12,0)")
    private Long id = 0L;

    @Column(columnDefinition = "NUMBER(8,0)")
    Integer exerciseNumber;

    @ManyToOne
    @JoinColumn(name="exercise_id", nullable=false, foreignKey = @ForeignKey(name = "exercise_test_ref_to_exercise_fk"))
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name="test_id", nullable=false, foreignKey = @ForeignKey(name = "exercise_test_ref_to_test_fk"))
    private Test test;
}
