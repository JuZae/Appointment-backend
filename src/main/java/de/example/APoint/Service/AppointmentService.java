package de.example.APoint.Service;

import de.example.APoint.Entity.AppointmentOption;

import java.util.UUID;

public interface AppointmentService {
    Boolean checkIfAppointmentIsInDB(UUID id);

    Boolean canEditAppointment(UUID id);

    AppointmentOption editAppointment(AppointmentOption appointmentOption);

    String getParticipantsById(UUID id);
}
