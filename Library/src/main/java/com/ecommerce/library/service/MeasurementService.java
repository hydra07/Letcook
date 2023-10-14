package com.ecommerce.library.service;

import com.ecommerce.library.model.Measurement;
import net.minidev.json.JSONArray;

import java.util.List;
import java.util.Optional;

public interface MeasurementService {
    List<Measurement> findAll();
    Measurement save(Measurement measurement);
    Optional<Measurement> findById(long id);
    Measurement update(Measurement measurement);
    void deleteById(long id);
    void enableById(long id);
    List<Measurement> findAllByActivated();

    Measurement findByName(String name);

    JSONArray findAllByActivatedJson();

}
