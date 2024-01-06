package de.example.APoint.Service;

import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.User;
import de.example.APoint.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public Boolean checkIfAppointmentIsInDB(UUID id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

}
