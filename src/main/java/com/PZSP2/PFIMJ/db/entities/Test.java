package com.PZSP2.PFIMJ.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "tests",
        indexes = {
                @Index(columnList = "subject_id", name = "test_to_subject_ix")
        }
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "subject"})
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_generator")
    @Column(name = "test_id", columnDefinition = "NUMBER(12,0)")
    private Long id = 0L;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 4000)
    private String description;

    @ManyToOne
    @JoinColumn(
            name="subject_id",
            nullable=false,
            foreignKey = @ForeignKey(name = "test_to_subject_fk")
    )
    private Subject subject;

    @OneToMany(mappedBy = "test")
    private Set<TestExercise> exercises = new HashSet<>();
}
