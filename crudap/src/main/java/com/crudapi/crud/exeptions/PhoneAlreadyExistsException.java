package com.crudapi.crud.exeptions;

public class PhoneAlreadyExistsException extends RuntimeException{

    public PhoneAlreadyExistsException(String phone) {
        super("Phone already exists: " + phone);
    }
}
