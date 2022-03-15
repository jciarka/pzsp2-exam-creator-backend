package com.PZSP2.PFIMJ.db.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class SubjectUserPK implements Serializable {
    @Column(name = "userid", nullable = false)
    private Long userId;
    @Column(name = "subjectid", nullable = false)
    private Long subjectId;
}
