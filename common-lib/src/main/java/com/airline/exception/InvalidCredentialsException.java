package com.airline.exception;

public class InvalidCredentialsException extends RuntimeException{

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public InvalidCredentialsException(
            String resourceName,
            String fieldName,
            Object fieldValue) {
        super(
                String.format(
                        "%s Invalid credentials for %s: '%s'",
                        resourceName,
                        fieldName,
                        fieldValue
                )
        );

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public InvalidCredentialsException(String message){
        super(message);
        this.resourceName = null;
        this.fieldName = null;
        this.fieldValue = null;
    }

    public String getResourceName(){
        return resourceName;
    }

    public  String getFieldName(){
        return fieldName;
    }

    public Object getFieldValue(){
        return fieldValue;
    }

}
