package de.example.APoint.DTO;

public class ParticipantDTO {
    private String appointmentId;
    private String participant;

    public ParticipantDTO(String appointmentId, String participant) {
        this.appointmentId = appointmentId;
        this.participant = participant;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
