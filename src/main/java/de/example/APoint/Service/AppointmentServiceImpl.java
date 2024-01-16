package de.example.APoint.Service;

import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.AppointmentOption;
import de.example.APoint.Exceptions.DeadlineExceededException;
import de.example.APoint.Repository.AppointmentOptionRepository;
import de.example.APoint.Repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentOptionRepository appointmentOptionRepository;

    @Override
    public Boolean checkIfAppointmentIsInDB(UUID id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean canEditAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        return LocalDateTime.now().isBefore(appointment.getDeadline());
    }

    @Override
    public AppointmentOption editAppointment(AppointmentOption appointmentOption) {
        if (canEditAppointment(appointmentOption.getFk_appID())) {
            // Perform the edit operation
            return appointmentOptionRepository.save(appointmentOption);
        } else {
            // Throw an exception or handle the case where the deadline is past
            throw new DeadlineExceededException("Deadline has passed");
        }
    }

    @Override
    public String getParticipantsById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        return appointment.getTeilnehmer();
    }
}
