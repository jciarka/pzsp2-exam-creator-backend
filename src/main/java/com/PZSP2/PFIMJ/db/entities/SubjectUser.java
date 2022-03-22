package com.PZSP2.PFIMJ.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subjectusers")
@NoArgsConstructor
public class SubjectUser {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectusers_generator")
//    private Long id;

    public SubjectUser(User user, Subject subject){
        setUser(user);
        setSubject(subject);
//        id = new SubjectUserPK();
//        id.setSubjectId(subject.getId());
//        id.setUserId(user.getId());
    }

    @EmbeddedId
    private SubjectUserPK id = new SubjectUserPK();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false, insertable = false, updatable = false)
    private Subject subject;

    @ManyToMany(
            fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "subjectuserroles",
            joinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
            },
            inverseJoinColumns = { @JoinColumn(name = "roleid") })
    private Set<SubjectRole> roles  = new HashSet<>();

    public void setUser(User user) {
        // remove from old
        if (this.user != null) {
            SubjectUser toRemove = this.user.getSubjectUsers().stream().filter(
                    t -> t.getId().getSubjectId().equals(getId().getSubjectId()))
                    .findFirst().orElse(null);
            if (toRemove != null)
                getUser().getSubjectUsers().remove(user);
        }

        this.getId().setUserId(user.getId());
        user.getSubjectUsers().add(this);
        this.user = user;
    }

    public void setSubject(Subject subject) {

        if (this.subject != null) {
            // remove from old
            SubjectUser toRemove = this.subject.getSubjectUsers().stream().filter(
                            t -> t.getId().getUserId().equals(getId().getUserId()))
                    .findFirst().orElse(null);

            if (toRemove != null)
                subject.getSubjectUsers().remove(user);
        }

        // add to new
        this.subject = subject;

        // add to this
        this.id.setSubjectId(subject.getId());
        subject.getSubjectUsers().add(this);
    }


    public void addRole(SubjectRole role) {
        this.roles.add(role);
        role.getSubjectUsers().add(this);
    }
    public void removeRole(long roleId) {
        SubjectRole role = this.roles.stream().filter(t -> t.getId().equals(roleId)).findFirst().orElse(null);
        if (role != null)
            this.roles.remove(role);
        role.getSubjectUsers().remove(this);
    }

    public void removeRole(String roleName) {
        SubjectRole role = this.roles.stream().filter(t -> t.getName().equals(roleName)).findFirst().orElse(null);
        if (role != null)
            this.roles.remove(role);
        role.getSubjectUsers().remove(this);
    }
}
