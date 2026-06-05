package com.druginteraction.main.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Patient {
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Integer age;
    private String gender;
}
