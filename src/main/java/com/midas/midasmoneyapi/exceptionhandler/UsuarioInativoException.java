package com.midas.midasmoneyapi.exceptionhandler;

public class UsuarioInativoException extends RuntimeException{

    public UsuarioInativoException(String message) {
        super(message);
    }
}
