package com.example.VacationCalculator.service;

import com.example.VacationCalculator.exception.DatesReversedProvidedException;
import com.example.VacationCalculator.exception.IncorrectDateProvidedException;
import com.example.VacationCalculator.exception.IncorrectSalaryProvidedException;
import com.example.VacationCalculator.exception.IncorrectWorkingDaysProvidedException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Year;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class VacationPaymentService {
    public String GetVacationPayment(double salary, String vacationStartString, String vacationEndString) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");

            LocalDate vacationStartDay = null;
            LocalDate vacationEndDay = null;

            try {
                vacationStartDay = LocalDate.parse(vacationStartString, formatter);
                vacationEndDay = LocalDate.parse(vacationEndString, formatter);
            } catch (IllegalArgumentException e) {
                throw new IncorrectDateProvidedException(
                        vacationStartDay == null ? vacationStartString : vacationEndString
                );
            }
            VacationPaymentValidation(salary, vacationStartDay, vacationEndDay);

            int workingDays = GetWorkingDays(vacationStartDay, vacationEndDay);

            return new DecimalFormat("#.##").format((salary / 12 / 29.3) * workingDays);
        } catch (DatesReversedProvidedException | IncorrectDateProvidedException
                | IncorrectSalaryProvidedException | IncorrectWorkingDaysProvidedException e) {
            return e.getMessage();
        }
    }
    public String GetVacationPayment(double salary, int days) {
        try {
            VacationPaymentValidation(salary, days);
            return new DecimalFormat("#.##").format((salary / 12 / 29.3) * days);
        }
        catch (IncorrectSalaryProvidedException | IncorrectWorkingDaysProvidedException e) {
            return e.getMessage();
        }
    }
    public int GetWorkingDays(LocalDate start, LocalDate end) {
        int days = 0;
        end = end.plusDays(1); // To include vacation last day
        HashSet<Integer> holidays = new HashSet<Integer>(Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, // New year and Xmas
                54, // Defender day
                82, // Women's day
                121, // Spring and Labor day
                129, // Victory day
                163, // Russia day
                308 // Unity day
        ));

        int yearDays = 365;
        if (Year.of(start.getYear()).isLeap()) yearDays++;

        // Count vacation days excluding weekends and holidays
        for (LocalDate i = start; i.isBefore(end); i = i.plusDays(1)) {
            if (i.getDayOfWeek() != 6 && i.getDayOfWeek() != 7 && !holidays.contains(i.getDayOfYear())) {
                days++;
            }
        }

        return days;
    }

    public void VacationPaymentValidation(double salary, LocalDate start, LocalDate end) {
        if (salary <= 0) throw new IncorrectSalaryProvidedException(salary);
        if (end.isBefore(start)) throw new DatesReversedProvidedException(start, end);
    }

    public void VacationPaymentValidation(double salary, int days) {
        if (salary <= 0 || Double.isNaN(salary)) throw new IncorrectSalaryProvidedException(salary);
        if (days <= 0) throw new IncorrectWorkingDaysProvidedException(days);
    }
}
