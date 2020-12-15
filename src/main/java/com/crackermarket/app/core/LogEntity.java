package com.crackermarket.app.core;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.StringTokenizer;

@Entity
@Table(name = "LOG_TABLE")
public class LogEntity extends BaseEntity {

    @Column(name= "DATE")
    private Date date;

    @Column(name = "TIME")
    private Time time;

    @Column(name = "MESSAGE")
    private String message;

    @Lob
    @Column(name = "STACKTRACE", length = Integer.MAX_VALUE)
    private String stackTrace;

    @Column(name = "CLASS")
    private String className;

    @Column(name = "METHOD")
    private String methodName;

    @Column(name = "HTTP_STATUS")
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private LogEntityType type;

    public LogEntity(LogEntityType type, Class cl, String methodName, String message){

        java.util.Date date = new java.util.Date();

        this.date = Date.valueOf(parseDate(date));
        this.time = Time.valueOf(parseTime(date));
        this.message = message;
        this.methodName = methodName;
        this.className = cl.getName();
        this.type = type;

        Logger logger = Logger.getLogger(cl);
        Logger thisLogger = Logger.getLogger(this.getClass());

        switch (this.type) {
            case FATAL: {
                logger.setLevel(Level.FATAL);
                logger.fatal(message);
                logger.trace(stackTrace);
            }
            case ERROR: {
                logger.setLevel(Level.ERROR);
                logger.error(message);
                logger.trace(stackTrace);
            }
            case INFO: {
                logger.setLevel(Level.INFO);
                logger.info(message);
                logger.trace(stackTrace);
            }
            case WARNING: {
                logger.setLevel(Level.WARN);
                logger.warn(message);
                logger.trace(stackTrace);
            }
            case DEBUG: {
                logger.setLevel(Level.DEBUG);
                logger.debug(message);
                logger.trace(stackTrace);
            }
            default: {
                thisLogger.setLevel(Level.ERROR);
                thisLogger.error("Wrong type of log!");
            }
        }
    }

    public LogEntity(String type, Class cl, String methodName, String message){

        java.util.Date date = new java.util.Date();

        this.date = Date.valueOf(parseDate(date));
        this.time = Time.valueOf(parseTime(date));
        this.message = message;
        this.methodName = methodName;
        this.className = cl.getName();
        this.setType(type);

        Logger logger = Logger.getLogger(cl);
        Logger thisLogger = Logger.getLogger(this.getClass());

        switch (this.type) {
            case FATAL: {
                logger.setLevel(Level.FATAL);
                logger.fatal(message);
                logger.trace(stackTrace);
            }
            case ERROR: {
                logger.setLevel(Level.ERROR);
                logger.error(message);
                logger.trace(stackTrace);
            }
            case INFO: {
                logger.setLevel(Level.INFO);
                logger.info(message);
                logger.trace(stackTrace);
            }
            case WARNING: {
                logger.setLevel(Level.WARN);
                logger.warn(message);
                logger.trace(stackTrace);
            }
            case DEBUG: {
                logger.setLevel(Level.DEBUG);
                logger.debug(message);
                logger.trace(stackTrace);
            }
            default: {
                thisLogger.setLevel(Level.ERROR);
                thisLogger.error("Wrong type of log!");
            }
        }
    }

    public LogEntity(LogEntityType type, Class cl, String methodName, HttpStatus status, String message, String stackTrace) {

        java.util.Date date = new java.util.Date();

        this.date = Date.valueOf(parseDate(date));
        this.time = Time.valueOf(parseTime(date));
        this.message = message;
        this.stackTrace = stackTrace;
        this.methodName = methodName;
        this.status = status.toString();
        this.className = cl.getName();
        this.type = type;

        Logger logger = Logger.getLogger(cl);
        Logger thisLogger = Logger.getLogger(this.getClass());

        switch (this.type){
            case FATAL:{
                logger.setLevel(Level.FATAL);
                logger.fatal(message);
                logger.trace(stackTrace);
            }
            case ERROR:{
                logger.setLevel(Level.ERROR);
                logger.error(message);
                logger.trace(stackTrace);
            }
            case INFO:{
                logger.setLevel(Level.INFO);
                logger.info(message);
                logger.trace(stackTrace);
            }
            case WARNING:{
                logger.setLevel(Level.WARN);
                logger.warn(message);
                logger.trace(stackTrace);
            }
            case DEBUG:{
                logger.setLevel(Level.DEBUG);
                logger.debug(message);
                logger.trace(stackTrace);
            }
            default:{
                thisLogger.setLevel(Level.ERROR);
                thisLogger.error("Wrong type of log!");
            }
        }

    }

    public LogEntity(String type, Class cl, String methodName, HttpStatus status, String message, String stackTrace) {

        java.util.Date date = new java.util.Date();

        this.date = Date.valueOf(parseDate(date));
        this.time = Time.valueOf(parseTime(date));
        this.message = message;
        this.stackTrace = stackTrace;
        this.methodName = methodName;
        this.status = status.toString();
        this.className = cl.getName();
        this.setType(type);

        Logger logger = Logger.getLogger(cl);
        Logger thisLogger = Logger.getLogger(this.getClass());

        switch (this.type){
            case FATAL:{
                logger.setLevel(Level.FATAL);
                logger.fatal(message);
                logger.trace(stackTrace);
            }
            case ERROR:{
                logger.setLevel(Level.ERROR);
                logger.error(message);
                logger.trace(stackTrace);
            }
            case INFO:{
                logger.setLevel(Level.INFO);
                logger.info(message);
                logger.trace(stackTrace);
            }
            case WARNING:{
                logger.setLevel(Level.WARN);
                logger.warn(message);
                logger.trace(stackTrace);
            }
            case DEBUG:{
                logger.setLevel(Level.DEBUG);
                logger.debug(message);
                logger.trace(stackTrace);
            }
            default:{
                thisLogger.setLevel(Level.ERROR);
                thisLogger.error("Wrong type of log!");
            }
        }

    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(/* yyyy-[m]m-[d]d */ String date) {
        this.date = Date.valueOf(date);
    }

    public Time getTime() {
        return time;
    }

    public void setTime(/* hh:mm:ss */String time) {
        this.time = Time.valueOf(time);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(Class cl) {
        this.className = cl.getName();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getType() {
        switch (type){
            case FATAL:{
                return "fatal";
            }
            case ERROR:{
                return "error";
            }
            case INFO:{
                return "info";
            }
            case WARNING:{
                return "warning";
            }
            case DEBUG:{
                return "debug";
            }
        }
        return "unknown";
    }

    public void setType(String newType) {
        switch(newType.toLowerCase()){
            case "fatal":{
                type = LogEntityType.FATAL;
                break;
            }
            case "error":{
                type = LogEntityType.ERROR;
                break;
            }
            case "warning": {
                type = LogEntityType.WARNING;
                break;
            }
            case "info": {
                type = LogEntityType.INFO;
                break;
            }
            case "debug": {
                type = LogEntityType.DEBUG;
                break;
            }
            default: {
                type = LogEntityType.UNKNOWN;
            }
        }
    }

    @Override
    public String toString() {
        return "LogEntity{" +
                "time=" + time +
                ", message='" + message + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                ", methodName='" + methodName + '\'' +
                ", type=" + type +
                '}';
    }

    private String parseDate(java.util.Date date){
        String startData = date.toString();
        StringTokenizer tokenizer = new StringTokenizer(startData, " ");

        tokenizer.nextToken();
        String month = tokenizer.nextToken();

        // Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
        switch (month.toLowerCase()){
            case "jan":{ month = "1";break; }
            case "feb":{ month = "2";break; }
            case "mar":{ month = "3";break; }
            case "apr":{ month = "4";break; }
            case "may":{ month = "5";break; }
            case "jun":{ month = "6";break; }
            case "jul":{ month = "7";break; }
            case "aug":{ month = "8";break; }
            case "sep":{ month = "9";break; }
            case "oct":{ month = "10";break; }
            case "nov":{ month = "11";break; }
            case "dec":{ month = "12";break; }
        }

        String day = tokenizer.nextToken();

        tokenizer.nextToken();
        tokenizer.nextToken();

        String year = tokenizer.nextToken();

        // To yyyy-[m]m-[d]d
        String result = year + "-" + month + "-" + day;

        return result;
    }

    private String parseTime(java.util.Date date){
        String startData = date.toString();
        StringTokenizer tokenizer = new StringTokenizer(startData,  " ");

        tokenizer.nextToken();
        tokenizer.nextToken();
        tokenizer.nextToken();

        String result = tokenizer.nextToken();

        return result;
    }

}
