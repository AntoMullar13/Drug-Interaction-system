package com.druginteraction.main.controller;

import com.druginteraction.main.Service.InteractionService;
import com.druginteraction.main.entity.Interaction_Request;
import com.druginteraction.main.entity.Interaction_Response;
import com.druginteraction.main.entity.Patient;
import com.druginteraction.main.entity.Prescription;
import com.druginteraction.main.repository.PatientRepo;
import com.druginteraction.main.repository.PrescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interaction")
public class InteractionController {
    @Autowired
    private PatientRepo pr1;
    @Autowired
    private PrescriptionRepo pr2;
    @Autowired
    private InteractionService intservice;
    @PostMapping("/check")
    public Interaction_Response getInteraction_response(@RequestBody Interaction_Request ir) {
        Interaction_Response response = new Interaction_Response();

        //Find patient:
        Patient p = pr1.findById(ir.getPatientid()).orElse(null);
        if (p==null) {
            response.setMessage("Patient not found");
            return response;
        }
        //getting prescripsions for the selected patient
        List <Prescription> listprescriptions = pr2.findByPatientId(p.getId());
        List<String> PatientMedNames = listprescriptions.stream().map(Prescription::getMedicineName).toList();
        String result = intservice.checkInteraction(PatientMedNames,ir.getMedicinename());
        response.setMessage(result);
        response.setStatus(ir.getMedicinename());
    return response;
    }

}
