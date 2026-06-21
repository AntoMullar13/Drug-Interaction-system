package com.druginteraction.main.entity;

import lombok.Data;

@Data
public class InteractionApiResponse {
    private String status;
    private Interaction interaction;
}

