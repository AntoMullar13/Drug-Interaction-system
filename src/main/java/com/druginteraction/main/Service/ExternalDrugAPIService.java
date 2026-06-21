package com.druginteraction.main.Service;
import com.druginteraction.main.entity.Interaction;
import com.druginteraction.main.entity.InteractionApiResponse;
import com.druginteraction.main.entity.Interaction_Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExternalDrugAPIService {

    @Value("${rxcheck.api.url}")
    private String apiUrl;

    @Value("${rxcheck.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public Interaction_Response checkDrugInteraction(List<String> existingMeds,
                                                     String newMedicine) {

        Interaction_Response finalResponse = new Interaction_Response();

        // Default response
        finalResponse.setStatus("SAFE");
        finalResponse.setSeverity("none");
        finalResponse.setMessage("No interaction found.");

        // Loop through all existing medicines
        for (String oldMedicine : existingMeds) {

            // API Headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Build URL
            String url = apiUrl +
                    "?drug1=" + oldMedicine +
                    "&drug2=" + newMedicine;

            // Call RxCheck API
            ResponseEntity<InteractionApiResponse> apiResponse =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            InteractionApiResponse.class);

            Interaction interaction = apiResponse.getBody().getInteraction();

            // If interaction found
            if (interaction.isFound()) {

                finalResponse.setStatus("DANGER");
                finalResponse.setSeverity(interaction.getSeverity());
                finalResponse.setRiskScore(interaction.getSeverity_score());

                // Clinical significance becomes your message
                finalResponse.setMessage(
                        interaction.getClinical_significance());

                // Store management advice
                finalResponse.setAlternative_Medicine(
                        interaction.getManagement());

                // Stop checking after first dangerous interaction
                break;
            }
        }

        return finalResponse;
    }
}