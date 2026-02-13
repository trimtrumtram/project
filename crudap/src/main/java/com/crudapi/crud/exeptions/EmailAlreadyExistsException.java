package com.crudapi.crud.exeptions;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
