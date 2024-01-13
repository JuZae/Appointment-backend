package de.example.APoint.Repository;

import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.AppointmentOption;
import de.example.APoint.Entity.User;
import de.example.APoint.Response.UserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

// This would return the whole User Entity with the password!
//    @Query("SELECT u FROM User u WHERE u.id = :id")
//    User findUserById(@Param("id") UUID id);

//This only returns the userinfo without the password
    @Query("SELECT new de.example.APoint.Response.UserResponse(u.id, u.username, u.email) FROM User u WHERE u.id = :id")
    UserResponse findUserById(@Param("id") UUID id);
}
