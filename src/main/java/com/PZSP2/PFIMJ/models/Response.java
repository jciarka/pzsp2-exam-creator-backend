package com.PZSP2.PFIMJ.models;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> extends EmptyResponse {
    private T model;

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
