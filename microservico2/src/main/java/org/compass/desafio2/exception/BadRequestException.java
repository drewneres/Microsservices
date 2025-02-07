package org.compass.desafio2.exception;

import lombok.Getter;

@Getter
public class MethodArgumentTypeMismatchException extends RuntimeException {
    private String resource;
    private String code;

    public MethodArgumentTypeMismatchException(String msg) {
        super(msg);
    }

    public MethodArgumentTypeMismatchException(String resource, String code) {
        super(code);
        this.resource = resource;
        this.code = code;
    }
}
