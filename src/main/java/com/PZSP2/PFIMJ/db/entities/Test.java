package com.PZSP2.PFIMJ.db.entities;

import com.PZSP2.PFIMJ.core.tests.TestContentConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_generator")
    @Column(name = "test_id")
    private Long id = 0L;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String description;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;

    @Lob
    @Convert(converter = TestContentConverter.class)
    private List<Exercise> exercises;
}
