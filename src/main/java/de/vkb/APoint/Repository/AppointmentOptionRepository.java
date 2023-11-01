package de.vkb.APoint.Repository;

import de.vkb.APoint.Entity.AppointmentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AppointmentOptionRepository extends JpaRepository<AppointmentOption, UUID>{

    @Query(value = "SELECT * FROM appointmentoptions a" +
            " INNER JOIN appointment ap ON ap.ap_id = a.ap_u_id" +
            " WHERE a.ap_u_id = ?1", nativeQuery = true)
    List<AppointmentOption> findByFK(UUID uuid);

}
