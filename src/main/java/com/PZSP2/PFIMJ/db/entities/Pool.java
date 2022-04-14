package com.PZSP2.PFIMJ.db.entities;

import com.PZSP2.PFIMJ.core.tests.TestContentConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(
        name = "Pools",
        indexes = {
                @Index(columnList = "subject_id", name = "pool_to_subject_ix"),
                @Index(columnList = "subject_id, name", name = "pool_to_subject_with_name_ix")
        }
)
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pools_generator")
    @Column(name = "pool_id", columnDefinition = "NUMBER(12,0)")
    private Long id = 0L;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 4000)
    private String description;

    @OneToMany(mappedBy = "pool")
    private Set<Exercise> exercises = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false, foreignKey = @ForeignKey(name = "pool_to_subject_fk"))
    private Subject subject;
}
