package de.example.APoint.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "DEADLINE")
    private LocalDateTime deadline;

    //User ID
    @Column(name = "FK_U_ID")
    @NotNull
    private UUID fk_userID;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBez() {
        return bez;
    }

    public void setBez(String bez) {
        this.bez = bez;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(String teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public UUID getFk_userID() {
        return fk_userID;
    }

    public void setFk_userID(UUID fk_userID) {
        this.fk_userID = fk_userID;
    }
}
