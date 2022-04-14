package com.PZSP2.PFIMJ.db.entities;

import com.PZSP2.PFIMJ.core.tests.ExerciseContentConverter;
import com.PZSP2.PFIMJ.core.tests.TestContentConverter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(
        name = "Exercises",
        indexes = {
                @Index(columnList = "pool_id", name = "exercise_to_pool_ix"),
        }
)
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercises_generator")
    @Column(name = "exercises_id", columnDefinition = "NUMBER(12,0)")
    private Long id = 0L;

    @Column(length = 100)
    String title;

    @Column(nullable = false, length = 20)
    String type;

    @Column(columnDefinition = "NUMBER(3,0)")
    Integer points;

    @ManyToOne
    @JoinColumn(name="pool_id", nullable=false, foreignKey = @ForeignKey(name = "exercise_to_pool_fk"))
    private Pool pool;

    @OneToMany(mappedBy = "exercise")
    private Set<TestExercise> tests = new HashSet<>();

    @Lob
    @Convert(converter = ExerciseContentConverter.class)
    List<ExerciseVersion> versions;
}
