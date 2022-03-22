package com.PZSP2.PFIMJ.db.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class SubjectUserPK implements Serializable {
    @Column(name = "user_id", nullable = false, columnDefinition = "NUMBER(9,0)")
    private Long userId;
    @Column(name = "subject_id", nullable = false, columnDefinition = "NUMBER(9,0)")
    private Long subjectId;
}
