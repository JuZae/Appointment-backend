package de.example.APoint.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private UUID id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "OPT_DAT")
    private LocalDateTime datum;

    @Column(name = "OPT_YES")
    private List<String> teilnehmerYes;

    @Column(name= "OPT_NO")
    private List<String> teilnehmerNo;

    //Appointment ID
    @Column(name = "AP_U_ID")
    @NotNull
    private UUID fk_appID;
}
