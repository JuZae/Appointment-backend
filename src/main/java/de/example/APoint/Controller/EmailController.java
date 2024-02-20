package de.example.APoint.Controller;

import de.example.APoint.POJO.EmailRequest;
import de.example.APoint.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;


    @PostMapping(value = "/sendEmail", consumes = "application/json", produces = "application/json")
    public ResponseEntity sendEmail(@RequestBody EmailRequest emailRequest) {
            emailService.sendEmailMultiple(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
            return ResponseEntity.ok("Email sent successfully.");
    }
}
