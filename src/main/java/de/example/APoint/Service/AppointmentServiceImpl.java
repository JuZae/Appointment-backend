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
        return LocalDateTime.now().isBefore(appointment.getDeadline());
    }

    @Override
    public AppointmentOption editAppointment(AppointmentOption incomingApo) {
        // Check if Appointment Deadline has passed yet
        if (canEditAppointment(incomingApo.getFk_appID())) {

            AppointmentOption dbApo = appointmentOptionRepository.findById(incomingApo.getId())
                    .orElseThrow(() -> new IllegalArgumentException("AppointmentOption not found with id: " + incomingApo.getId()));

            // Get yes and no lists from DB and turn into list if is null
            List<String> yesDB = dbApo.getTeilnehmerYes() != null ? dbApo.getTeilnehmerYes() : new ArrayList<>();
            List<String> noDB = dbApo.getTeilnehmerNo() != null ? dbApo.getTeilnehmerNo() : new ArrayList<>();

            // Do the same with incoming
            List<String> yesIncoming = incomingApo.getTeilnehmerYes() != null ? incomingApo.getTeilnehmerYes() : new ArrayList<>();
            List<String> noIncoming = incomingApo.getTeilnehmerNo() != null ? incomingApo.getTeilnehmerNo() : new ArrayList<>();

            // For each participant in the incoming yes list, add to the DB yes list if not already present and remove from DB no list
            for (String participant : yesIncoming) {
                if (!yesDB.contains(participant)) {
                    yesDB.add(participant); //Add to yes list if not already present
                }
                noDB.remove(participant); // Remove from no list if present
            }

            // For each participant in the incoming no list, add to the DB no list if not already present and remove from DB yes list
            for (String participant : noIncoming) {
                if (!noDB.contains(participant)) {
                    noDB.add(participant);
                }
                yesDB.remove(participant); // Remove from yes list if present
            }

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
}
