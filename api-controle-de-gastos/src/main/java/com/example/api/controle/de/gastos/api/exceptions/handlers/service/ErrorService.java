package com.example.api.controle.de.gastos.api.exceptions.handlers.service;

import com.example.api.controle.de.gastos.api.exceptions.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {

    public Error buildError(HttpStatus status, String localizedMessage, StringBuffer url, String method) {
        return new Error(status.value(), localizedMessage, url.toString(), method);
    }

}
