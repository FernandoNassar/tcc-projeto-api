package com.example.api.controle.de.gastos.api.exceptions.handlers.common;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.api.exceptions.handlers.service.ErrorService;
import com.example.api.controle.de.gastos.api.exceptions.model.Error;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CommonExceptions {

    private final ErrorService errorService;


    public CommonExceptions(ErrorService errorService) {
        this.errorService = errorService;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest req) {

        var responseBody = errorService.buildError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), req.getRequestURL(), req.getMethod());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Error> HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletRequest req) {

        var responseBody = errorService.buildError(HttpStatus.METHOD_NOT_ALLOWED, e.getLocalizedMessage(), req.getRequestURL(), req.getMethod());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(responseBody);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error ResourceNotFoundExceptionHandler(ResourceNotFoundException e, HttpServletRequest req) {
        var status = HttpStatus.NOT_FOUND;
        return errorService.buildError(status, e.getLocalizedMessage(), req.getRequestURL(), req.getMethod());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest req) {
        var status = HttpStatus.BAD_REQUEST;
        return errorService.buildError(status, e.getLocalizedMessage(), req.getRequestURL(), req.getMethod());
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e, HttpServletRequest req) {
        var status = HttpStatus.BAD_REQUEST;
        return errorService.buildError(status, e.getLocalizedMessage(), req.getRequestURL(), req.getMethod());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e, HttpServletRequest req) {
        var status = HttpStatus.BAD_REQUEST;
        return errorService.buildError(status, e.getLocalizedMessage(), req.getRequestURL(), req.getMethod());
    }
}
