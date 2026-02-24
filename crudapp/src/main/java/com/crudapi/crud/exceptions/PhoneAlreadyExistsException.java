package com.crudapi.crud.exceptions;

public class PhoneAlreadyExistsException extends RuntimeException{

    public PhoneAlreadyExistsException(String phone) {
        super("Phone already exists: " + phone);
    }
}
