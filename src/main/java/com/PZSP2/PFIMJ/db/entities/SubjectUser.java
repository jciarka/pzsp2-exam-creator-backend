package com.PZSP2.PFIMJ.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subjectusers")
public class SubjectUser {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectusers_generator")
//    private Long id;

    public SubjectUser(User user, Subject subject){
        setUser(user);
        setSubject(subject);
    }

    @EmbeddedId
    private SubjectUserPK id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subjectid", nullable = false, insertable = false, updatable = false)
    private Subject subject;

    @ManyToMany(
            fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "appsubjectuserroles",
            joinColumns = { @JoinColumn(name = "userid"), @JoinColumn(name = "subjectid") },
            inverseJoinColumns = { @JoinColumn(name = "roleid") })
    private Set<SubjectRole> roles;

    public void setUser(User user) {
        // remove from old
        SubjectUser toRemove = this.user.getSubjectUsers().stream().filter(
                t -> t.getId().getSubjectId().equals(getId().getSubjectId()))
                .findFirst().orElse(null);
        if (toRemove != null)
            getUser().getSubjectUsers().remove(user);

        // add to this
        this.getId().setUserId(user.getId());
        this.user.getSubjectUsers().add(this);

        // add to new
        this.user = user;
    }

    public void setSubject(Subject subject) {
        // remove from old
        SubjectUser toRemove = this.subject.getSubjectUsers().stream().filter(
                t -> t.getId().getUserId().equals(getId().getUserId()))
                .findFirst().orElse(null);

        if (toRemove != null)
            subject.getSubjectUsers().remove(user);

        // add to this
        this.id.setSubjectId(user.getId());
        this.user.getSubjectUsers().add(this);

        // add to new
        this.subject = subject;
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
