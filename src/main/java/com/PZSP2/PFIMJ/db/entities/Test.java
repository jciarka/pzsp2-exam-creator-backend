package com.PZSP2.PFIMJ.db.entities;

import com.PZSP2.PFIMJ.core.tests.TestContentConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "tests",
        indexes = {
            @Index(columnList = "test_id", name = "test_id_ix"),
            @Index(columnList = "subject_id", name = "test_to_subject_ix")
        }
)
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
    @JoinColumn(name="subject_id", nullable=false, foreignKey = @ForeignKey(name = "test_to_subject_fk"))
    private Subject subject;

    @Lob
    @Convert(converter = TestContentConverter.class)
    private List<Exercise> exercises;
}
