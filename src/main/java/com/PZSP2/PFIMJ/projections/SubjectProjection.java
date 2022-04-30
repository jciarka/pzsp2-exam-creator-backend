package com.PZSP2.PFIMJ.projections;


import com.PZSP2.PFIMJ.db.entities.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectProjection {
    private Long id;
    private String name;
    private String description;

    public SubjectProjection(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.description = subject.getDescription();
    }
}
