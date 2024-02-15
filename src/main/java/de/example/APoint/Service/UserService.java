package de.example.APoint.Service;

import de.example.APoint.DTO.UserDTO;
import de.example.APoint.DTO.LoginDTO;

import de.example.APoint.Entity.User;
import de.example.APoint.Response.LoginResponse;

import java.util.UUID;

public interface UserService {

    LoginResponse registerUser(UserDTO userDTO);

    boolean verifyUser(String token);

    LoginResponse loginUser(LoginDTO loginDTO);

    Boolean checkIfUserIsInDB(UUID id);

    void resendVerificationEmailByEmail(String email);

    void createVerificationTokenForUser(User user, String token);
}
