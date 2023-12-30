package de.example.APoint.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="appointmentoptions")
public class AppointmentOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "APO_ID")
    UUID id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "OPT_DAT")
    LocalDateTime datum;

    @Column(name = "OPT_YES")
    List<String> teilnehmerYes;

    @Column(name= "OPT_NO")
    List<String> teilnehmerNo;

    //Appointment ID
    @Column(name = "AP_U_ID")
    UUID fk_appID;
}
