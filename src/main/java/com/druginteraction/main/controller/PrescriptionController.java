package com.druginteraction.main.controller;

import com.druginteraction.main.entity.Prescription;
import com.druginteraction.main.repository.PrescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {
    @Autowired
    private PrescriptionRepo PriscribeR1;

    @GetMapping
    public List<Prescription> getPrescriptions() {
        return PriscribeR1.findAll();
    }

    @GetMapping("/{id}")
    public Prescription getPrescription(@PathVariable Integer id) {
        return PriscribeR1.findById(id).get();
    }

    @GetMapping("/patient/{id}")
    public List<Prescription> getPatientPrescriptions(@PathVariable Long id) {
        return PriscribeR1.findByPatientId(id);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable Integer id) {
        PriscribeR1.deleteById(id);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable Integer id, @RequestBody Prescription prescription) {
        Prescription p = PriscribeR1.findById(id).orElse(null);
        if (p != null) {
            prescription.setMedicineName(p.getMedicineName());
            prescription.setDosageMg(p.getDosageMg());
            prescription.setFrequencyperday(p.getFrequencyperday());
            prescription.setDurationDays(p.getDurationDays());
            prescription.setPrescribedDate(LocalDate.now());
        }
        return PriscribeR1.save(prescription);
    }
}
