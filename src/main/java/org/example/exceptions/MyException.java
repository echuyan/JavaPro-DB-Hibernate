package org.example.exceptions;

public class MyException extends RuntimeException{
    private final int status;

    public MyException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
