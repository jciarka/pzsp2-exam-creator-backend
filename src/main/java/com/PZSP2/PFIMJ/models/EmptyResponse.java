package com.PZSP2.PFIMJ.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmptyResponse {
    private boolean ok;
    private Iterable<String> errors;
}
