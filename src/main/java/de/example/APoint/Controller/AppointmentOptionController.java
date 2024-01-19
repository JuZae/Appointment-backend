package de.example.APoint.Controller;

import de.example.APoint.DTO.ParticipantDTO;
import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.AppointmentOption;
import de.example.APoint.Exceptions.DeadlineExceededException;
import de.example.APoint.Repository.AppointmentOptionRepository;
import de.example.APoint.Repository.AppointmentRepository;
import de.example.APoint.Service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/public/opt")
public class AppointmentOptionController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentOptionRepository appointmentOptionRepository;

    @Autowired
    AppointmentService appointmentService;

    //Update the AppointmentOption
    @PutMapping("/edit")
    public ResponseEntity<?> editAppointment(@RequestBody AppointmentOption appointmentOption) {
        try {
            var response = appointmentService.editAppointment(appointmentOption);
            return ResponseEntity.ok(response);
        } catch (DeadlineExceededException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Deadline has passed");
        }
    }

    //Get All AppointmentOptions associated with Appointment id
    @GetMapping("/getOpt/{appId}")
    public ResponseEntity<?> getAppointmentOptions(@PathVariable String appId){
        var response = appointmentOptionRepository.findByFk_appID(UUID.fromString(appId));
        return ResponseEntity.ok(response);
    }

    //Get Appointment by Appointment id
    @GetMapping("/getApp/{appId}")
    public ResponseEntity<?> getAppointment(@PathVariable String appId){
        var response = appointmentRepository.findById(UUID.fromString(appId));
        return ResponseEntity.ok(response);
    }

    //Get Participants associated with Appointment by id
    @GetMapping("/getParti/{appId}")
    public ResponseEntity<String> getParticipants(@PathVariable String appId) {
        try {
            String participants = appointmentService.getParticipantsById(UUID.fromString(appId));
            return ResponseEntity.ok(participants);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add/addParticipant")
    public ResponseEntity<?> addParticipant(@RequestBody ParticipantDTO participantDto) {
        try {
            Appointment appointment = appointmentRepository.findById(UUID.fromString(participantDto.getAppointmentId())).orElseThrow();
            List<String> participants = getStrings(participantDto, appointment);

            // Put list back together and turn it into a string
            String updatedParticipantsStr = String.join(",", participants);
            appointment.setTeilnehmer(updatedParticipantsStr);

            appointmentRepository.save(appointment);

            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    private static List<String> getStrings(ParticipantDTO participantDto, Appointment appointment) {
        String participantsStr = appointment.getTeilnehmer();

        // Split participants into an Arraylist
        List<String> participants = new ArrayList<>();
        if (participantsStr != null && !participantsStr.isEmpty()) {
            participants = new ArrayList<>(Arrays.asList(participantsStr.split(",")));
        }

        // If participant is not present then add to list
        if (!participants.contains(participantDto.getParticipant())) {
            participants.add(participantDto.getParticipant());
        }
        return participants;
    }

    @DeleteMapping("/deleteAppOpt/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable UUID appointmentId) {
        try {
            appointmentService.deleteAppointmentAndOptions(appointmentId);
            return ResponseEntity.ok().body("Appointment and associated options deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}
