package com.midas.midasmoneyapi.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String mensagemDev = ex.getCause().toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
        ExceptionBody body = ExceptionBody.builder()
                .timestamp(LocalDateTime.now())
                .status(status.toString())
                .error(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<Erro> erros = errosList(ex.getBindingResult());
        ExceptionBody body = ExceptionBody.builder()
                .timestamp(LocalDateTime.now())
                .status(status.toString())
                .error(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler({EmptyResultDataAccessException.class, NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(RuntimeException ex, WebRequest request){
        ExceptionBody body = ExceptionBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .error(ex.toString())
                .build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request){
        ExceptionBody body = ExceptionBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .error(ex.toString())
                .build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UsuarioInativoException.class})
    public ResponseEntity<Object> handleUsuarioInativoException(UsuarioInativoException ex, WebRequest request){
        ExceptionBody body = ExceptionBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .error(ex.toString())
                .build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UsuarioNaoEncontradoException.class})
    public ResponseEntity<Object> handleUsuarioNaoEncontradoException(UsuarioInativoException ex, WebRequest request){
        ExceptionBody body = ExceptionBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .error(ex.toString())
                .build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> errosList(BindingResult bindingResult){
        List<Erro> erros = new ArrayList<>();

        for(FieldError error : bindingResult.getFieldErrors()){
            String mensagemUsuario = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            String mensagemDev = error.toString();
            erros.add(new Erro(mensagemUsuario, mensagemDev));
        }

        return erros;
    }

    private static class Erro {
        private String mensagemUsuario;
        private String mensagemDev;

        public Erro(String mensagemUsuario, String mensagemDev) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensagemDev = mensagemDev;
        }

        public String getMensagemUsuario() {
            return mensagemUsuario;
        }

        public String getMensagemDev() {
            return mensagemDev;
        }
    }
}
