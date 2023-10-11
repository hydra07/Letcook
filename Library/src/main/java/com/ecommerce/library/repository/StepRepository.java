package com.ecommerce.library.repository;

import com.ecommerce.library.model.Recipe;
import com.ecommerce.library.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

}