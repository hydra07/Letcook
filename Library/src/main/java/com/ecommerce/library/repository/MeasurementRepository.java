package com.ecommerce.library.repository;

import com.ecommerce.library.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    @Query("select m from Measurement m where m.is_deleted = false")
    List<Measurement> findAllByActivated();

    //get measurement by name
    @Query("select m from Measurement m where m.name = ?1")
    Measurement findByName(String name);
}
