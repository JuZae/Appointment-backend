package de.example.APoint.DTO;

import java.util.UUID;

public class AppointmentDTO {
    private UUID id;
    private String bez;
    private String ort;
    private String teilnehmer;
    private String beschreibung;
    private UUID fk_userID;

    public AppointmentDTO(UUID id, String bez, String ort, String teilnehmer, String beschreibung, UUID fk_userID) {
        this.id = id;
        this.bez = bez;
        this.ort = ort;
        this.teilnehmer = teilnehmer;
        this.beschreibung = beschreibung;
        this.fk_userID = fk_userID;
    }

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

    public UUID getFk_userID() {
        return fk_userID;
    }

    public void setFk_userID(UUID fk_userID) {
        this.fk_userID = fk_userID;
    }
}
