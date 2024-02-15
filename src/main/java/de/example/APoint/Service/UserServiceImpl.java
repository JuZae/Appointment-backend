package de.example.APoint.Service;

import de.example.APoint.DTO.LoginDTO;
import de.example.APoint.DTO.UserDTO;
import de.example.APoint.Entity.User;
import de.example.APoint.Entity.VerificationToken;
import de.example.APoint.Enum.Role;
import de.example.APoint.Repository.UserRepository;
import de.example.APoint.Repository.VerificationTokenRepository;
import de.example.APoint.Response.LoginResponse;
import de.example.APoint.Util.JwtUtil;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    @Override
    public LoginResponse registerUser(UserDTO userDTO) {
        // Check if user with the same username or email already exists in the DB
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return new LoginResponse("Username already taken", false, null, null);
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return new LoginResponse("Email already in use", false, null, null);
        }

        // If user doesn't exist, create a new user
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setVerified(false); // User is not verified initially
        user.setRole(Role.USER); // Default role
        userRepository.save(user);

        sendVerificationEmail(user);

        return new LoginResponse("User registered successfully. Please check your email to verify your account.", true, null, user.getId());
    }

    @Override
    public void resendVerificationEmailByEmail(@Email String email) {
        User user;
        try {
            user = userRepository.findByEmail(email);
            if (!user.isVerified()) {
                sendVerificationEmail(user);
            } else {
                throw new IllegalArgumentException("User already verified");
            }
        }catch(IllegalArgumentException i) {
            new IllegalArgumentException("Invalid E-Mail");
        }
    }

    private void sendVerificationEmail(User user) {
        // Generate verification token
        String token = UUID.randomUUID().toString();
        createVerificationTokenForUser(user, token);

        // Construct verification URL
        String verificationUrl = appUrl + "/verify?token=" + token;

        // Use HTML content for the body
        String emailBody = "<h1>Welcome to APoint!</h1>"
                + "<p>Thanks for signing up. Please click the link below to verify your email address and complete your registration:</p>"
                + "<a href='" + verificationUrl + "'>Verify Email</a>"
                + "<p>If you did not sign up for an APoint account, please ignore this email.</p>";

        emailService.sendEmail(user.getEmail(), "Verify your account", emailBody);
    }

    @Override
    public boolean verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiryDate = verificationToken.getExpiryDate();
            // Basic check if the token has not expired
            if (expiryDate != null && now.isBefore(expiryDate)) {
                User user = verificationToken.getUser();
                user.setVerified(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);

        // Set expiration date for the token to 24 hours from now using LocalDateTime
        LocalDateTime expiryDate = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        verificationToken.setExpiryDate(expiryDate);

        verificationTokenRepository.save(verificationToken);
    }


    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        User userDBCheck = userRepository.findByEmail(loginDTO.getEmail());

        if(userDBCheck != null) {
            if (!userDBCheck.isVerified()) {
                return new LoginResponse("User is not verified. Please check your email to verify your account.", false, null, null);
            }

            String password = loginDTO.getPassword();
            String encodedPassword = userDBCheck.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

            if(isPwdRight) {
                String token = jwtUtil.generateToken(userDBCheck.getEmail());
                return new LoginResponse("Login Success", true, token, userDBCheck.getId());
            } else {
                return new LoginResponse("Password doesn't match", false, null, null);
            }
        } else {
            return new LoginResponse("Email does not exist", false, null, null);
        }
    }


    @Override
    public Boolean checkIfUserIsInDB(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}