package com.druginteraction.main.controller;

import com.druginteraction.main.entity.Doctor;
import com.druginteraction.main.entity.Patient;
import com.druginteraction.main.repository.DoctorRepo;
import com.druginteraction.main.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorRepo dr1;
    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return dr1.save(doctor);
    }

    @GetMapping
    public List<Doctor> getDoctors() {
        return dr1.findAll();
    }
    @DeleteMapping("{id}")
    public void deleteDoctor(@PathVariable Long id) {
        dr1.deleteById(id);
    }
    @PutMapping("{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        Doctor d = dr1.findById(id).orElse(null);
        if(d != null){
            d.setDoctor_name(doctor.getDoctor_name());
            d.setSpecialty(doctor.getSpecialty());
            d.setEmail(doctor.getEmail());
        }
        return dr1.save(d);
    }
}
