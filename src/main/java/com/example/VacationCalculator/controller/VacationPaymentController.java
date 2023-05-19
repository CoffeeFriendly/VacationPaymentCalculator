package com.example.VacationCalculator.controller;

import com.example.VacationCalculator.service.VacationPaymentService;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
public class VacationPaymentController {
    @Autowired
    VacationPaymentService vacationService;

    @GetMapping(value = "/calculate", params = {"salary", "days"})
    public ResponseEntity getVacationPayment (@RequestParam int salary, @RequestParam(name = "days") int vacationDays) {
        return ResponseEntity.ok().body(vacationService.GetVacationPayment(salary, vacationDays));
    }
    @GetMapping(value = "/calculate", params = {"salary", "start", "end"})
    public ResponseEntity getVacationPayment(@RequestParam double salary,
                                             @RequestParam(name = "start") String vacationStartDayInput,
                                             @RequestParam(name = "end") String vacationEndDayInput) {

        return ResponseEntity.ok().body(vacationService
                .GetVacationPayment(salary, vacationStartDayInput, vacationEndDayInput));
    }
}
