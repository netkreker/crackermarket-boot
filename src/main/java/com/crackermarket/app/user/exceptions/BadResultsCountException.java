package com.crackermarket.app.user.exceptions;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class BadResultsCountException extends Exception {

    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadResultsCountException that = (BadResultsCountException) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    public BadResultsCountException(String message){
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
