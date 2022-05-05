package com.example.api.controle.de.gastos.api.exceptions.model;

import java.time.Instant;

public record Error(
        Instant timeStamp,
        Integer Status,
        String message,
        String path,
        String method
) {

    public Error(Integer status, String message, String path, String method) {
        this(Instant.now(), status, message, path, method);
    }
}
