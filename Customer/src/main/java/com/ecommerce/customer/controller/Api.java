package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.service.MeasurementService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Api {
    @Autowired
    private MeasurementService measurementService;
    @GetMapping("/measurement")
    public JSONArray getMeansurement() {
        return measurementService.findAllByActivatedJson();
    }
}
