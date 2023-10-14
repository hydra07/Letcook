package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.repository.MeasurementRepository;
import com.ecommerce.library.service.MeasurementService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    @Autowired
    private MeasurementRepository measurementRepository;
    @Override
    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Override
    public Measurement save(Measurement measurement) {
        Measurement measurementSave = new Measurement(measurement.getName());
        return measurementRepository.save(measurementSave);
    }

    @Override
    public Optional<Measurement> findById(long id) {
        return measurementRepository.findById(id);
    }

    @Override
    public Measurement update(Measurement measurement) {
        Measurement measurementUpdate = null;
        try {
            measurementUpdate = measurementRepository.findById(measurement.getId()).get();
            measurementUpdate.setName(measurement.getName());
            measurementUpdate.set_deleted(measurement.is_deleted());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return measurementRepository.save(measurementUpdate);
    }

    @Override
    public void deleteById(long id) {
        Measurement measurement = measurementRepository.getById(id);
        measurement.set_deleted(true);
        measurementRepository.save(measurement);
    }

    @Override
    public void enableById(long id) {
        Measurement measurement = measurementRepository.getById(id);
        measurement.set_deleted(false);
        measurementRepository.save(measurement);
    }

    @Override
    public List<Measurement> findAllByActivated() {
        return measurementRepository.findAllByActivated();
    }

    @Override
    public Measurement findByName(String name) {
        return measurementRepository.findByName(name);
    }

    @Override
    public JSONArray findAllByActivatedJson() {
        JSONArray jsonArray = new JSONArray();
        List<Measurement> measurementList = measurementRepository.findAllByActivated();
        for(Measurement measurement : measurementList){
            jsonArray.add(measurement.getName());
        }
        return jsonArray;
    }
}
