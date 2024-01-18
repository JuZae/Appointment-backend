package de.example.APoint.Repository;

import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.AppointmentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
@Repository
public interface AppointmentOptionRepository extends JpaRepository<AppointmentOption, UUID>{

//    @Query(value = "SELECT * FROM appointmentoptions a" +
//            " INNER JOIN appointment ap ON ap.ap_id = a.ap_u_id" +
//            " WHERE a.ap_u_id = ?1", nativeQuery = true)
//    List<AppointmentOption> findByFK(UUID uuid);

    @Query("SELECT a FROM AppointmentOption a WHERE a.fk_appID = :appId")
    List<AppointmentOption> findByFk_appID(@Param("appId") UUID appId);

}
