package br.com.mercado_souto.util.exception;

public class DataAlreadyExistsException extends RuntimeException {

    public DataAlreadyExistsException(String field) {
        super(String.format("A record with this %s already exists.", field));
    }
}
