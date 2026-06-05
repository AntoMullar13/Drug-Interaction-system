package com.druginteraction.main.repository;

import com.druginteraction.main.entity.Doctor;
import com.druginteraction.main.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
}
