package de.example.APoint.Service;

import de.example.APoint.DTO.LoginDTO;
import de.example.APoint.DTO.UserDTO;
import de.example.APoint.Entity.User;
import de.example.APoint.Repository.UserRepository;
import de.example.APoint.Response.LoginResponse;
import de.example.APoint.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())
        );


        userRepository.save(user);
        return user.getUsername();
    }

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
        User user = new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())
        );

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse("User registered successfully", true, token, user.getId());
    }


    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        User userDBCheck = userRepository.findByEmail(loginDTO.getEmail());

        if(userDBCheck != null) {
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
