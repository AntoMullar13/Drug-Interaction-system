package com.druginteraction.main.repository;

import com.druginteraction.main.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepo extends JpaRepository<Prescription, Integer> {
    List<Prescription> findByPatientId(Long id);
}
