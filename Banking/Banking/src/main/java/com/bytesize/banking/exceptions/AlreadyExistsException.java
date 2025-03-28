package com.bytesize.banking.exceptions;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(String message)
    {
        super(message);
    }

}
