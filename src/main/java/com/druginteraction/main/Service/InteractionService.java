package com.druginteraction.main.Service;

import com.druginteraction.main.entity.Interaction_Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionService {
    @Autowired
    private ExternalDrugAPIService apiService;
    public Interaction_Response checkInteraction(List<String> existingMed, String newmed) {
        return apiService.checkDrugInteraction(existingMed, newmed);
        }
    }