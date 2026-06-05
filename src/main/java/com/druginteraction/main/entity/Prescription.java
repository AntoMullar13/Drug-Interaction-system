package com.druginteraction.main.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.time.LocalDate;

@Entity
@Data
public class Prescription {
    @ManyToOne
    @JoinColumn (name = "patient_id")
    private Patient patient;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;
    private Integer dosageMg;
    private Integer Frequencyperday;
    private Integer durationDays;
    private LocalDate prescribedDate;

}
