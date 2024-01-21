package de.example.APoint.Controller;

import de.example.APoint.DTO.UserDTO;
import de.example.APoint.Entity.User;
import de.example.APoint.Repository.UserRepository;
import de.example.APoint.Response.UserResponse;
import de.example.APoint.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:9000", "http://49.13.170.189:9000"})
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/oauth2")
    public OAuth2User getUser(@AuthenticationPrincipal OAuth2User principal) {
        return principal;
    }


//TODO: POTENTIAL SECURITY THREAT! Since no Auth check here! FIX THIS
    @GetMapping("/getOne/{email}")
    public User getOneUser(@PathVariable @Email String email) {
        return userRepository.findByEmail(email);
    }

    @GetMapping("/get/{id}")
    public UserResponse getOneUserById(@PathVariable String id) {
        return userRepository.findUserById(UUID.fromString(id));
    }
}
