package com.reactive.reporting.ReactiveJasperReport.controller;

import com.reactive.reporting.ReactiveJasperReport.service.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    JasperReportService jasperReportService;
    @GetMapping("/message")
    public String showMessage() {

        byte[] bytes = jasperReportService.generateReport();
        System.out.println("18 Bytes "+bytes);
        return "Hello, this is a Spring Boot application!";

    }
}
