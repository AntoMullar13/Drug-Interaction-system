package com.druginteraction.main.controller;

import com.druginteraction.main.entity.Doctor;
import com.druginteraction.main.entity.Patient;
import com.druginteraction.main.entity.Prescription;
import com.druginteraction.main.repository.DoctorRepo;
import com.druginteraction.main.repository.PrescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.druginteraction.main.repository.PatientRepo;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired private PatientRepo pr1;
    @Autowired private DoctorRepo dr1;
    @Autowired private PrescriptionRepo pr2;
    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return pr1.save(patient);
    }

    @GetMapping()
    public List<Patient> getAllPatient() {
        return pr1.findAll();
    }

    @GetMapping("{id}")
    public Patient getPatientById(@PathVariable long id) {
        return pr1.findById(id).orElse(null);
    }
    @DeleteMapping("{id}")
    public String deletePatientById(@PathVariable long id) {
        if(pr1.existsById(id)){
            pr1.deleteById(id);
            return "Patient with id: " + id + " has been deleted";
        }
        else {
            return "Patient with id: " + id + " does not exist";
        }
    }
    @PutMapping("{id}")
    public Patient updatePatientById(@PathVariable long id, @RequestBody Patient patient) {
        Patient p = pr1.findById(id).orElse(null);
        if(p != null){
            p.setName(patient.getName());
            p.setAge(patient.getAge());
            p.setGender(patient.getGender());
        }
        return pr1.save(p);
    }
    @PutMapping("{id}/doctor/{doctor_id}")
    public Patient assignDoctorByPatientId(@PathVariable long id, @PathVariable long doctor_id) {
        Patient p = pr1.findById(id).orElse(null);
        Doctor d = dr1.findById(doctor_id).orElse(null);
        if(p != null && d != null){
            p.setDoctor(d);
        }
        return pr1.save(p);
    }

    //Add prescriptions
    @PostMapping("/{patient_id}/prescriptions")
    public Prescription addPrescription(@PathVariable long patient_id, @RequestBody Prescription prescription) {
        Patient p = pr1.findById(patient_id).orElse(null);
        prescription.setPatient(p);
        return pr2.save(prescription);
    }
}