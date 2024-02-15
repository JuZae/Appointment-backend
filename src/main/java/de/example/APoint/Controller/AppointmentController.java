package de.example.APoint.Controller;

import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.AppointmentOption;
import de.example.APoint.Repository.AppointmentOptionRepository;
import de.example.APoint.Repository.AppointmentRepository;
import de.example.APoint.Repository.UserRepository;
import de.example.APoint.Service.AppointmentService;
import de.example.APoint.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/app")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentOptionRepository appointmentOptionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppointmentOptionRepository optionRepository;

    @Autowired
    UserService userService;

    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/CreateNewAppointment")
    public ResponseEntity createNewAppointment(@RequestBody Appointment appointment) {
        appointmentRepository.save(appointment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

//    @GetMapping("/del")
//    public ResponseEntity deleteAll(){
//        userRepository.deleteAll();
//        appointmentRepository.deleteAll();
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

    /**
     * Checks if user is in database before saving the appointment
     * @param appointment
     * @return ResponseEntity
     */
    @PostMapping("/saveApp")
    public ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment) {
        Boolean isUserInDB = userService.checkIfUserIsInDB(appointment.getFk_userID());
        if(isUserInDB) {
            Appointment appointmentResponse  = appointmentRepository.save(appointment);

            return ResponseEntity.ok(appointmentResponse);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Persist AppointmentOption
    @PostMapping("/saveAppOption")
    public ResponseEntity<?> saveAppointmentOption(@RequestBody AppointmentOption appointmentOption) {
            return ResponseEntity.ok(appointmentOptionRepository.save(appointmentOption));
    }

    //Get all Appointments that belong to User (by id)
    @GetMapping("/getAppByUserId/{id}")
    public List<Appointment> getAllAppointmentsByUserID(@PathVariable String id) {
        return appointmentRepository.findByFkUserID(UUID.fromString(id));
    }

    //Get one Appointment by id
    @GetMapping("/getAppById/{id}")
    public Optional<Appointment> getAllAppointmentsByAppID(@PathVariable String id) {
        return appointmentRepository.findById(UUID.fromString(id));
    }

//    @DeleteMapping("/deleteAppOption/{optionId}")
//    public ResponseEntity<?> deleteAppointment(@PathVariable UUID appointmentId) {
//        try {
//            appointmentService.deleteAppointmentAndOptions(appointmentId);
//            return ResponseEntity.ok().body("Appointment and associated options deleted successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
//        }
//    }

    @DeleteMapping("/deleteAppOption/{optionId}")
    public ResponseEntity<?> deleteAppointmentOption(@PathVariable UUID optionId) {
        System.out.println("DELETE IS CALLED: " + optionId);
        appointmentService.deleteAppointmentOption(optionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAppById/{appId}")
    public ResponseEntity<?> deleteAppById(@PathVariable UUID appId) {
        System.out.println("DELETE IS CALLED: " + appId);
        appointmentService.deleteAppointmentAndOptions(appId);
        return ResponseEntity.ok().build();
    }
}
