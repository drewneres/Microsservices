package org.compass.desafio2.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private String resource;
    private String code;

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String resource, String code) {
        this.resource = resource;
        this.code = code;
    }
}