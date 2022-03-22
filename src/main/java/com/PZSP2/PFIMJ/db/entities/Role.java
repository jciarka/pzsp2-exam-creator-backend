package com.PZSP2.PFIMJ.db.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "global_roles_dict",
        uniqueConstraints = {
                @UniqueConstraint(name = "name_unique_k", columnNames = {"name"})
        },
        indexes = {
            @Index(columnList = "role_id", name = "global_role_id_ix"),
            @Index(columnList = "name", name = "global_role_name_ix")
        }
)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @Column(name = "role_id", columnDefinition = "NUMBER(4,0)")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;
}
