package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private MeasurementService measurementService;

    @GetMapping("/measurements")
    public ResponseEntity<List<String>> getMeasurements() {
        List<Measurement> measurementsObject = measurementService.findAllByActivated();
        List<String> measurements = new ArrayList<>();
        for (Measurement measurement : measurementsObject) {
            measurements.add(measurement.getName());
        }
        System.out.println("danhsach:" + measurements);

        return ResponseEntity.ok(measurements);
    }
}







