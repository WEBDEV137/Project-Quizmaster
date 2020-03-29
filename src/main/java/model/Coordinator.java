package model;

import java.util.ArrayList;

public class Coordinator extends User{

    //CONSTRUCTOR
    public Coordinator(String inlognaam, String wachtwoord, String rol) {
        super(inlognaam, wachtwoord, rol);
        tasks = new ArrayList<>();
        getAllTasks().add("Quiz aanmaken/wijzigen");
        getAllTasks().add("Quiz beheer");
    }
}