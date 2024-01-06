package de.example.APoint.Service;

import de.example.APoint.DTO.LoginDTO;
import de.example.APoint.DTO.UserDTO;
import de.example.APoint.Entity.User;
import de.example.APoint.Repository.UserRepository;
import de.example.APoint.Response.LoginResponse;
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
    public LoginResponse loginUser(LoginDTO loginDTO) {
        User userDBCheck = userRepository.findByEmail(loginDTO.getEmail());

        if(userDBCheck != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = userDBCheck.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

            if(isPwdRight) {
                Optional<User> userEmailPasswordCheck = userRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if(userEmailPasswordCheck.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("Password doesn't match", false);
            }
        } else {
            return new LoginResponse("Email does not exist", false);
        }
    }

    @Override
    public Boolean checkIfUserIsInDB(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            // Logik, wenn der Benutzer gefunden wird
            return true;
        } else {
            // Logik, wenn der Benutzer nicht gefunden wird
            return false;
        }
    }
}
