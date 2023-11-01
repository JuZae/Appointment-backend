package de.vkb.APoint.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="registeredusers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "U_ID")
    UUID id;

    @Column(name = "UNAME")
    String username;

    @Column(name = "PWRD")
    String password;
}
