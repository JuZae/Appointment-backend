package de.example.APoint.Repository;

import de.example.APoint.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    @Query("SELECT a FROM Appointment a WHERE a.fk_userID = :fkUserID")
    List<Appointment> findByFkUserID(@Param("fkUserID") UUID fkUserID);
}
