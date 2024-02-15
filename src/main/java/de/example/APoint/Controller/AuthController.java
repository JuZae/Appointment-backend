package de.example.APoint.Controller;

import de.example.APoint.DTO.LoginDTO;
import de.example.APoint.DTO.UserDTO;
import de.example.APoint.Response.LoginResponse;
import de.example.APoint.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        LoginResponse loginResponse = userService.registerUser(userDTO);
        if(loginResponse.getStatus()) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.badRequest().body(loginResponse);
        }
    }

//    @GetMapping("/verify")
//    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
//        userService.verifyUser(token);
//        return ResponseEntity.ok("User verified successfully");
//    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        boolean isVerified = userService.verifyUser(token);
        System.out.println("USER IST VERIFIZIERT: " + isVerified);
        if (isVerified) {
            return ResponseEntity.ok().body(Map.of("message", "User verified successfully", "verified", true));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Verification failed. Invalid or expired token.", "verified", false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResponse loginResponse = userService.loginUser(loginDTO);
        if (loginResponse.getStatus()) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.badRequest().body(loginResponse);
        }
    }


}