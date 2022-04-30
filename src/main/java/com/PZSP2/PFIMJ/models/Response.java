package com.PZSP2.PFIMJ.models;

import java.util.ArrayList;
import java.util.Enumeration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class Response<T> extends EmptyResponse {
    private T model;
    public static <Y> ResponseEntity<Response<Y>> GetPositive(Y body, HttpStatus status)
    {
        return new ResponseEntity<>(new Response<>(body, true), status);
    }

    public static <Y> ResponseEntity<Response<Y>> GetNegative(Y body, HttpStatus status, Iterable<String> errors)
    {
        return new ResponseEntity<>(new Response<>(body, false, errors), status);
    }

    public static <Y> ResponseEntity<Response<Y>> GetNegative(Y body, HttpStatus status)
    {
        return new ResponseEntity<>(new Response<>(body, false), status);
    }

    public Response(T model) {
        this.model = model;
        this.setOk(true);
        this.setErrors(new ArrayList<>());
    }

    public Response(T model, boolean success) {
        this.model = model;
        this.setOk(success);
        this.setErrors(new ArrayList<>());
    }

    public Response(T model, boolean success, Iterable<String> errors) {
        this.model = model;
        this.setOk(success);
        this.setErrors(errors);
    }
}
