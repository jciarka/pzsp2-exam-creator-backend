package com.PZSP2.PFIMJ.models.subject;

import com.PZSP2.PFIMJ.db.entities.SubjectRole;
import com.PZSP2.PFIMJ.db.entities.SubjectUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ParticipantModel {
    private Long userId;

    private  Long subjectId;
    private String firstname;
    private String lastname;
    private String email;
    private String position;
    private String academicTitle;
    private String institute;
    private String faculty;
    private List<SubjectRole> subjectRoles;

    public ParticipantModel(SubjectUser subjectUser) {
        userId = subjectUser.getId().getUserId();
        subjectId = subjectUser.getId().getSubjectId();
        firstname = subjectUser.getUser().getFirstname();
        lastname = subjectUser.getUser().getLastname();
        email = subjectUser.getUser().getEmail();
        position = subjectUser.getUser().getPosition();
        academicTitle = subjectUser.getUser().getAcademicTitle();
        institute = subjectUser.getUser().getInstitute();
        faculty = subjectUser.getUser().getFaculty();
        subjectRoles = subjectUser.getRoles().stream().collect(Collectors.toList());
    }
}
