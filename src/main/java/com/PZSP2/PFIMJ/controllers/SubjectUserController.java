package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.db.entities.SubjectUser;
import com.PZSP2.PFIMJ.models.EmptyResponse;
import com.PZSP2.PFIMJ.models.Response;
import com.PZSP2.PFIMJ.projections.SubjectProjection;
import com.PZSP2.PFIMJ.projections.SubjectUserWithRoles;
import com.PZSP2.PFIMJ.services.SubjectUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value="api/subjectuser")
public class SubjectUserController extends ControllerBase {

    public final SubjectUsersService subjectUsersService;

    @Autowired
    public SubjectUserController(SubjectUsersService subjectUsersService) {
        this.subjectUsersService = subjectUsersService;
    }

    @PostMapping(value="/add",consumes="application/json")
    public SubjectUser addSubjectUser(@RequestBody SubjectUserWithRoles sur){
        return subjectUsersService.addUserToSubject2(sur.getUserId(),sur.getSubjectId(),sur.getSubjectRoles());
    }

    @DeleteMapping("{subjectId}/{userId}")
    public ResponseEntity<EmptyResponse> removeUserSubjects(@PathVariable Long subjectId, @PathVariable Long userId) {
        if (!isAuthenticated()){
            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.UNAUTHORIZED);
        }

        Long adminId = getAuthenticatedUser().getId();
        SubjectUser admin = subjectUsersService.get(adminId, subjectId);
        if (admin == null || !admin.getRoles().contains("ADMIN")){
            return new ResponseEntity<>(
                new EmptyResponse(false, Arrays.asList("You dont have permissions to remove user from subject")),
                HttpStatus.UNAUTHORIZED);
        }

        subjectUsersService.removeUserFromSubject(userId, subjectId);
        return new ResponseEntity<>(new EmptyResponse(true), HttpStatus.OK);
    }

    @PostMapping("{subjectId}/{userId}/{role}")
    public ResponseEntity<EmptyResponse> addSubjectRoleToUser(
            @PathVariable Long subjectId, @PathVariable Long userId, @PathVariable String role
        )
    {
        if(subjectId == null || userId == null || role == null) {
            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.BAD_REQUEST);
        }

        if (!isAuthenticated()){
            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.UNAUTHORIZED);
        }

        Long adminId = getAuthenticatedUser().getId();
        SubjectUser admin = subjectUsersService.get(adminId, subjectId);
        if (admin == null || !admin.getRoles().stream().anyMatch(x -> x.getName().equals("ADMIN"))){
            return new ResponseEntity<>(
                    new EmptyResponse(false, Arrays.asList("You dont have permissions to remove user from subject")),
                    HttpStatus.UNAUTHORIZED);
        }

        subjectUsersService.addUserToRole(userId, subjectId, role);
        return new ResponseEntity<>(new EmptyResponse(true), HttpStatus.OK);
    }

    @DeleteMapping("{subjectId}/{userId}/{role}")
    public ResponseEntity<EmptyResponse> removeSubjectRoleFromUser(
            @PathVariable Long subjectId, @PathVariable Long userId, @PathVariable String role
    )
    {
        if(subjectId == null || userId == null || role == null) {
            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.BAD_REQUEST);
        }

        if (!isAuthenticated()){
            return new ResponseEntity<>(new EmptyResponse(false), HttpStatus.UNAUTHORIZED);
        }

        Long adminId = getAuthenticatedUser().getId();
        SubjectUser admin = subjectUsersService.get(adminId, subjectId);
        if (admin == null || !admin.getRoles().stream().anyMatch(x -> x.getName().equals("ADMIN"))){
            return new ResponseEntity<>(
                    new EmptyResponse(false, Arrays.asList("You dont have permissions to remove user from subject")),
                    HttpStatus.UNAUTHORIZED);
        }

        subjectUsersService.removeUserFromRole(userId, subjectId, role);
        return new ResponseEntity<>(new EmptyResponse(true), HttpStatus.OK);
    }
}
