package com.druginteraction.main.entity;

import lombok.Data;

@Data
public class Interaction_Response {
    private String Severity;
    private String Alternative_Medicine;
    private Integer RiskScore;
    private String status;
    private String Message;
}
