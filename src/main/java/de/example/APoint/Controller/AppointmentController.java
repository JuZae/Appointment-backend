package de.example.APoint.Controller;

import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.AppointmentOption;
import de.example.APoint.Entity.User;
import de.example.APoint.Repository.AppointmentOptionRepository;
import de.example.APoint.Repository.AppointmentRepository;
import de.example.APoint.Repository.UserRepository;
import de.example.APoint.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/api/app")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppointmentOptionRepository optionRepository;

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    @GetMapping("/getUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/CreateNewAppointment")
    public ResponseEntity createNewAppointment(@RequestBody Appointment appointment) {
        appointmentRepository.save(appointment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/del")
    public ResponseEntity deleteAll(){
        userRepository.deleteAll();
        appointmentRepository.deleteAll();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //TODO: check if userID is in database!

    /**
     * Checks if user is in database before saving the appointment
     * @param appointment
     * @return ResponseEntity
     */
    @PostMapping("/saveApp")
    public ResponseEntity saveAppointment(@RequestBody Appointment appointment) {
        Boolean isUserInDB = userService.checkIfUserIsInDB(appointment.getFk_userID());
        if(isUserInDB) {
            appointmentRepository.save(appointment);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getAppByUserId/{id}")
    public List<Appointment> getAllAppointmentsByUserID(@PathVariable String id) {
        System.out.println("ISCALLEDXXXXX");
        return appointmentRepository.findByFkUserID(UUID.fromString(id));
    }

    @GetMapping("/getAppById/{id}")
    public Optional<Appointment> getAllAppointmentsByAppID(@PathVariable String id) {
        return appointmentRepository.findById(UUID.fromString(id));
    }

    @PostMapping("/updateAppById/{id}")
    public List<AppointmentOption> updatedAppointments(@PathVariable String id, @RequestBody AppointmentOption appointmentOption) {
        appointmentOption.setFk_appID(UUID.fromString(id));
        optionRepository.save(appointmentOption);
        return optionRepository.findByFK(UUID.fromString(id));
    }

    @GetMapping("/getOptions/{id}")
    public List<AppointmentOption> getUpdatedAppointments(@PathVariable String id) {
        return optionRepository.findByFK(UUID.fromString(id));
    }

}
