package com.ex.mobilestore.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(Integer id) {
        super("Not found with id: " + id);
    }

    public NotFoundException(String name) {
        super("Not found with name: " + name);
    }
}
