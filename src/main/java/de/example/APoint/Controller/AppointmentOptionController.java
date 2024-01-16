package de.example.APoint.Controller;

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

import java.util.Optional;
import java.util.UUID;

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
        var response = appointmentOptionRepository.findByFK(UUID.fromString(appId));
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
}
