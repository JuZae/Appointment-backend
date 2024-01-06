package de.example.APoint.Service;

import java.util.UUID;

public interface AppointmentService {
    Boolean checkIfAppointmentIsInDB(UUID id);
}
