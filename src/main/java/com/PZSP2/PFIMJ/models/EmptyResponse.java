package com.PZSP2.PFIMJ.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmptyResponse {
    private boolean ok;
    private Iterable<String> errors;

    public EmptyResponse(boolean success) {
        ok = success;
    }
}
