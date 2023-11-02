package de.vkb.APoint.Service;

import de.vkb.APoint.Entity.Appointment;
import de.vkb.APoint.Entity.AppointmentOption;
import de.vkb.APoint.Entity.User;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Faker {



    public ArrayList<Appointment> createFakeAppointments(UUID uuid){
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();

        Appointment appointment1 = new Appointment();
        appointment1.setBez("Malle");
        appointment1.setOrt("Playa");
        appointment1.setTeilnehmer("hans, peter");
        appointment1.setBeschreibung("nix");
        appointment1.setFk_userID(uuid);


        Appointment appointment2 = new Appointment();
        appointment2.setBez("Essen");
        appointment2.setOrt("Schinken Peter");
        appointment2.setTeilnehmer("etstets");
        appointment2.setBeschreibung("Lebakas");
        appointment2.setFk_userID(uuid);

        appointmentArrayList.add(appointment1);
        appointmentArrayList.add(appointment2);

        return appointmentArrayList;
    }

    public List<AppointmentOption> createFakeOptions(UUID uuid){
        List<AppointmentOption> optionsList = new ArrayList<>();
        List<String> input = new ArrayList<>();
        input.add("Test1");
        input.add("Test2");

        AppointmentOption option1 = new AppointmentOption();
        option1.setTeilnehmerYes(input);
        option1.setTeilnehmerNo(input);
        option1.setDatum(LocalDateTime.now());
        option1.setFk_appID(uuid);

        optionsList.add(option1);

        return optionsList;
    }
}
