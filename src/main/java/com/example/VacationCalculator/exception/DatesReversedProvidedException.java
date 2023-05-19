package com.example.VacationCalculator.exception;


import org.joda.time.LocalDate;

public class DatesReversedProvidedException extends RuntimeException{
    public DatesReversedProvidedException(LocalDate start, LocalDate end) {
        super("Vacation can't end before starting.\n" +
                "Vacation start provided: " + start + "\n" +
                "Vacation end provided: " + end);
    }
}
