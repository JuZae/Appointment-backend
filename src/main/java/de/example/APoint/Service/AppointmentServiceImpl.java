package de.example.APoint.Service;

import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.AppointmentOption;
import de.example.APoint.Exceptions.DeadlineExceededException;
import de.example.APoint.Repository.AppointmentOptionRepository;
import de.example.APoint.Repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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
        System.out.println("Appointment Deadline: " + appointment.getDeadline());
        System.out.println("Time: " + LocalDateTime.now());
        return LocalDateTime.now().isBefore(appointment.getDeadline());
    }

    @Override
    public AppointmentOption editAppointment(AppointmentOption incomingApo) {
        // Check if Appointment Deadline has passed yet
        if (canEditAppointment(incomingApo.getFk_appID())) {

            AppointmentOption dbApo = appointmentOptionRepository.findById(incomingApo.getId())
                    .orElseThrow(() -> new IllegalArgumentException("AppointmentOption not found with id: " + incomingApo.getId()));

            // Initialize lists from DB, use empty list if null
            List<String> yesDB = dbApo.getTeilnehmerYes() != null ? dbApo.getTeilnehmerYes() : new ArrayList<>();
            List<String> noDB = dbApo.getTeilnehmerNo() != null ? dbApo.getTeilnehmerNo() : new ArrayList<>();

            // Process each participant in incoming 'Yes' list
            for (String participant : incomingApo.getTeilnehmerYes()) {
                if (!yesDB.contains(participant)) {
                    yesDB.add(participant); // Add to 'Yes' list if not already present
                }
                noDB.remove(participant); // Remove from 'No' list if present
            }

            // Process each participant in incoming 'No' list
            for (String participant : incomingApo.getTeilnehmerNo()) {
                if (!noDB.contains(participant)) {
                    noDB.add(participant); // Add to 'No' list if not already present
                }
                yesDB.remove(participant); // Remove from 'Yes' list if present
            }

            // Update the AppointmentOption with modified lists
            dbApo.setTeilnehmerYes(yesDB);
            dbApo.setTeilnehmerNo(noDB);

            // Persist updated AppointmentOption to DB
            return appointmentOptionRepository.save(dbApo);
        } else {
            throw new DeadlineExceededException("Deadline has exceeded!");
        }
    }


    @Override
    public String getParticipantsById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        return appointment.getTeilnehmer();
    }

    @Override
    @Transactional
    public void deleteAppointmentAndOptions(UUID appointmentId) {
        // First delete all appointmentOptions associated with the appointment
        List<AppointmentOption> options = appointmentOptionRepository.findByFk_appID(appointmentId);
        appointmentOptionRepository.deleteAll(options);

        //Then, delete appointment itself
        appointmentRepository.deleteById(appointmentId);
    }


    @Override
    public AppointmentOption saveAppointmentOption(AppointmentOption appointmentOption) {
        return appointmentOptionRepository.save(appointmentOption);
    }

    @Override
    public void deleteAppointmentOption(UUID optionId) {
        appointmentOptionRepository.deleteById(optionId);
    }
}
