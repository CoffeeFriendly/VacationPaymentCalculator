package com.example.VacationCalculator.exception;

public class IncorrectWorkingDaysProvidedException extends RuntimeException{
    public IncorrectWorkingDaysProvidedException(int days) {
        super("Working days should be greater than 0 and can't be null\n" +
                "Working days provided: " + days);}
}
