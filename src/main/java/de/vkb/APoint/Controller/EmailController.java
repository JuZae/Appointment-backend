package de.vkb.APoint.Controller;

import de.vkb.APoint.POJO.EmailRequest;
import de.vkb.APoint.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/sendEmail")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @PostMapping
    public ResponseEntity sendEmail(@RequestBody EmailRequest emailRequest) {
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
            return ResponseEntity.ok("Email sent successfully.");
    }
}
