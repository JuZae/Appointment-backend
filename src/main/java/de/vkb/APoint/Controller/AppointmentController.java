package de.vkb.APoint.Controller;

import de.vkb.APoint.Entity.Appointment;
import de.vkb.APoint.Entity.AppointmentOption;
import de.vkb.APoint.Entity.User;
import de.vkb.APoint.Repository.AppointmentOptionRepository;
import de.vkb.APoint.Repository.AppointmentRepository;
import de.vkb.APoint.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppointmentOptionRepository optionRepository;

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

    @PostMapping("/getObj")
    public List<Appointment> getObjectFromFrontend(@RequestBody Appointment location) {
        appointmentRepository.save(location);
        return appointmentRepository.findAll();
    }

    /**
     * How to save an Appointment with corresponding User ID (maybe ResponseBody instead of pathvariable)
     */
    //TODO: check if User id is in database (new method for that) and not null
    @PostMapping("/saveApp/{id}")
    public ResponseEntity saveAppointmentWithId(UUID id, @RequestBody Appointment appointment) {
        if(id != null) {
            appointment.setFk_userID(id);
            appointmentRepository.save(appointment);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/getUserById/{id}")
    public List<Appointment> getUsersTest(@PathVariable String id) {
        return userRepository.findAppointmentsById(UUID.fromString(id));
    }

    @GetMapping("/getAppById/{id}")
    public List<Appointment> getAppointmentsById(@PathVariable String id) {
        return appointmentRepository.findAllById(Collections.singleton(UUID.fromString(id)));
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
