package de.vkb.APoint.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name="appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "AP_ID")
    UUID id;

    @Column(name = "BEZ")
    String bez;

    @Column(name = "ORT")
    String ort;

    @Column(name = "TEILN")
    String teilnehmer;

    @Column(name = "BESCHR")
    String beschreibung;

    @Column(name = "FK_U_ID")
    UUID fk_userID;
}
