package com.PZSP2.PFIMJ.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subjects")
@NoArgsConstructor
public class Subject {
    public  Subject(String name, String description)
    {
        setName(name);
        setDescription(description);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_generator")
    private Long id = 0L;

    @Column(nullable = false)
    private String name;

    @Column(length = 4000)
    private String description;

    @OneToMany(mappedBy = "subject")
    private Set<SubjectUser> subjectUsers;

    @OneToMany(mappedBy = "subject")
    private Set<Test> tests;

    public void AddTest(Test test) {
        test.setSubject(this);
        tests.add(test);
    }

    public void RemoveTest(Test test)
    {
        RemoveTest(test.getId());
    }

    public void RemoveTest(Long testId)
    {
        Test toRemove = this.tests.stream().filter(t -> t.getId().equals(testId))
                .findFirst().orElse(null);
        if (toRemove != null) {
            this.tests.remove(toRemove);
            toRemove.setSubject(null);
        }
    }
}
