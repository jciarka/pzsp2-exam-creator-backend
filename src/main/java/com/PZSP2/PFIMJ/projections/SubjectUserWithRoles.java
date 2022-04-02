package com.PZSP2.PFIMJ.projections;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectUserWithRoles {
    Long userId;
    Long subjectId;
    List<Long> subjectRoles;

    public SubjectUserWithRoles(Long userId, Long subjectId, List<Long> subjectRoles) {
        this.userId = userId;
        this.subjectId = subjectId;
        this.subjectRoles = subjectRoles;
    }
}
