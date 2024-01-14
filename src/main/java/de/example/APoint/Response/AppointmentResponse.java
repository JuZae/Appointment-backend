package de.example.APoint.Response;

import java.util.UUID;

public class AppointmentResponse {
    private UUID id;
    private String message;

    public AppointmentResponse(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
