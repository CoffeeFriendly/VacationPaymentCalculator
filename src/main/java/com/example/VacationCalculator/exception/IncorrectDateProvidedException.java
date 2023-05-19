package com.example.VacationCalculator.exception;

public class IncorrectDateProvidedException extends RuntimeException{
    public IncorrectDateProvidedException(String date) {
        super("Exception occured while trying to type conversion provided date string.\n" +
                "Date have to be in a \"dd-MM-yyyy\" format, i.e. \"18-05-2023\"\n" +
                "Date provided: " + (date.length() == 0 ? "null" : date));
    }
}
