package de.vkb.APoint.Repository;

import de.vkb.APoint.Entity.Appointment;
import de.vkb.APoint.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value =
            "SELECT *" +
            " FROM appointment a" +
                    " INNER JOIN registeredusers u ON u.u_id = a.user_u_id" +
                    " WHERE u.u_id = ?1",nativeQuery = true)
    List<Appointment> findAppointmentsById(UUID uuid);

}
