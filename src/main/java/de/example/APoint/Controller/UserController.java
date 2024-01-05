package de.example.APoint.Controller;

import de.example.APoint.Entity.User;
import de.example.APoint.Repository.UserRepository;
import de.example.APoint.Service.UserService;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
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
}
