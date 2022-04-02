package com.PZSP2.PFIMJ.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(
        name = "users_subject_assign",
        indexes = {
            @Index(columnList = "subject_id", name = "user_subject_assign_to_subject_fk"),
            @Index(columnList = "user_id", name = "user_subject_assign_to_user_ix"),
        }
)
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "subject"})
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
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "user_subject_assign_to_subject_fk")
    )
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "subject_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "user_subject_assign_to_user_fk")
    )
    private Subject subject;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                CascadeType.REMOVE,
                CascadeType.REMOVE
            }
     )
    @JoinTable(
            name = "users_subject_roles",
            indexes = {
                @Index(columnList = "user_id", name = "subject_role_to_user_assign_user_ix"),
                @Index(columnList = "subject_id", name = "subject_role_to_user_assign_subject_ix"),
                @Index(columnList = "role_id", name = "subject_role_to_role_ix")
            },
            joinColumns = {
                @JoinColumn(
                        name = "user_id",
                        referencedColumnName = "user_id",
                        foreignKey = @ForeignKey(name = "subject_role_to_user_assign_fk")
                ),
                @JoinColumn(
                        name = "subject_id",
                        referencedColumnName = "subject_id",
                        foreignKey = @ForeignKey(name = "subject_role_to_user_assign_fk"))
            },
            inverseJoinColumns = { @JoinColumn(name = "role_id",
                    referencedColumnName = "role_id",
                    foreignKey = @ForeignKey(name = "subject_role_to_role_fk"))
            }
    )
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
