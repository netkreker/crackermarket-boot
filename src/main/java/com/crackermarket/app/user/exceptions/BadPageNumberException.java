package com.crackermarket.app.user.exceptions;

import java.util.Objects;

public class BadPageNumberException extends Exception {

    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadPageNumberException that = (BadPageNumberException) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    public BadPageNumberException(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message.toString();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
