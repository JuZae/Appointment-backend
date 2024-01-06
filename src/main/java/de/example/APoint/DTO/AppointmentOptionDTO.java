package de.example.APoint.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AppointmentOptionDTO {
    private UUID id;
    private LocalDateTime datum;
    private List<String> teilnehmerYes;
    private List<String> teilnehmerNo;
    private UUID fk_appID;

    public AppointmentOptionDTO(UUID id, LocalDateTime datum, List<String> teilnehmerYes, List<String> teilnehmerNo, UUID fk_appID) {
        this.id = id;
        this.datum = datum;
        this.teilnehmerYes = teilnehmerYes;
        this.teilnehmerNo = teilnehmerNo;
        this.fk_appID = fk_appID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public List<String> getTeilnehmerYes() {
        return teilnehmerYes;
    }

    public void setTeilnehmerYes(List<String> teilnehmerYes) {
        this.teilnehmerYes = teilnehmerYes;
    }

    public List<String> getTeilnehmerNo() {
        return teilnehmerNo;
    }

    public void setTeilnehmerNo(List<String> teilnehmerNo) {
        this.teilnehmerNo = teilnehmerNo;
    }

    public UUID getFk_appID() {
        return fk_appID;
    }

    public void setFk_appID(UUID fk_appID) {
        this.fk_appID = fk_appID;
    }
}
