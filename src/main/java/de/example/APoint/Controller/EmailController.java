package de.example.APoint.Controller;

import de.example.APoint.POJO.EmailRequest;
import de.example.APoint.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:9000", "http://49.13.170.189:9000"})
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;


    @PostMapping("/sendEmail")
    public ResponseEntity sendEmail(@RequestBody EmailRequest emailRequest) {
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
            return ResponseEntity.ok("Email sent successfully.");
    }
}
