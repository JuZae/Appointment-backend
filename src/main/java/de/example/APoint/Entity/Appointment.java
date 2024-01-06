package de.example.APoint.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name="appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "AP_ID")
    @NotNull
    private UUID id;

    @Column(name = "BEZ")
    private String bez;

    @Column(name = "ORT")
    private String ort;

    @Column(name = "TEILN")
    private String teilnehmer;

    @Column(name = "BESCHR")
    private String beschreibung;

    //User ID
    @Column(name = "FK_U_ID")
    @NotNull
    private UUID fk_userID;
}
