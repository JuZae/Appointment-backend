package de.example.APoint.Service;

import de.example.APoint.DTO.UserDTO;
import de.example.APoint.DTO.LoginDTO;

import de.example.APoint.Response.LoginResponse;

import java.util.UUID;

public interface UserService {
    String addUser(UserDTO userDTO);

    LoginResponse registerUser(UserDTO userDTO);


    LoginResponse loginUser(LoginDTO loginDTO);

    Boolean checkIfUserIsInDB(UUID id);
}
