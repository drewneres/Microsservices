package org.compass.desafio2.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private String resource;
    private String code;

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String resource, String code) {
        this.resource = resource;
        this.code = code;
    }
}