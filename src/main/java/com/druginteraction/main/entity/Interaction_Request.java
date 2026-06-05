package com.druginteraction.main.entity;

import lombok.Data;

@Data
public class Interaction_Request {

    private Long patientid;
    private String medicinename;
    private Integer dosage;
}
