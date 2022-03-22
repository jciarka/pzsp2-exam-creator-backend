package com.PZSP2.PFIMJ.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subject_roles_dict")
@NoArgsConstructor
public class SubjectRole {

    public SubjectRole(String name)
    {
        setName(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectroles_generator")
    @Column(name = "role_id")
    private Long id = 0L;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "roles")
    @JsonIgnore
    private Set<SubjectUser> subjectUsers = new HashSet<>();
}
