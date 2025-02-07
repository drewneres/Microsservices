package org.compass.desafio2.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private String resource;
    private String code;
    private String msg;

    public BadRequestException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BadRequestException(String resource, String code) {
        super("Recurso " + resource + " com ID " + code + " não encontrado.");
        this.resource = resource;
        this.code = code;
    }
}
