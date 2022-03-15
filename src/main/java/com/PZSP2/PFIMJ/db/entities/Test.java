package com.PZSP2.PFIMJ.db.entities;

import lombok.Getter;
import lombok.Setter;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_generator")
    private Long id = 0L;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String description;

    @ManyToOne
    @JoinColumn(name="subjectid", nullable=false)
    private Subject subject;
}
