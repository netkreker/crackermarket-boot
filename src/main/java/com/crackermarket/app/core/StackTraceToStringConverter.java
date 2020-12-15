package com.crackermarket.app.core;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceToStringConverter {

    static public String toString(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        String stackTrace = stringWriter.toString();
        return stackTrace;
    }
}
