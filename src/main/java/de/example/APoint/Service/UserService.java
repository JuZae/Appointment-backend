package de.example.APoint.Service;

import de.example.APoint.DTO.UserDTO;
import de.example.APoint.DTO.LoginDTO;

import de.example.APoint.Response.LoginResponse;

public interface UserService {
    String addUser(UserDTO userDTO);
    LoginResponse loginUser(LoginDTO loginDTO);
}
