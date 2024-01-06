package de.example.APoint.Repository;

import de.example.APoint.Entity.Appointment;
import de.example.APoint.Entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

//    @Query(value =
//            "SELECT *" +
//                    " FROM appointment a" +
//                    " INNER JOIN registeredusers u ON u.u_id = a.fk_u_id" +
//                    " WHERE u.u_id = ?1",nativeQuery = true)
//    List<Appointment> findAppointmentsById(UUID uuid);


    Optional<User> findOneByEmailAndPassword(String email, String password);
    User findByEmail(String email);

}
