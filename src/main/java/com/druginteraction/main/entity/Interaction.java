package com.druginteraction.main.entity;

import lombok.Data;

@Data
public class Interaction {

        private boolean found;
        private String severity;
        private Integer severity_score;
        private String mechanism;
        private String clinical_significance;
        private String management;
        private String evidence_level;
    }
