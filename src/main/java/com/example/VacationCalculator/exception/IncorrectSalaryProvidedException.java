package com.example.VacationCalculator.exception;

public class IncorrectSalaryProvidedException extends RuntimeException {
    public IncorrectSalaryProvidedException(double salary) {
        super("Salary should be greater than 0 and can't be null.\n" +
                "Salary provided: " + salary);}
}
