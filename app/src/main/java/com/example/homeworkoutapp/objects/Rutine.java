package com.example.homeworkoutapp.objects;

import android.util.EventLogTags;

//Clase para guardar los datos de las rutinas
public class Rutine {
    public int id = 0;
    public String name = "Rutina";
    public String Description = "La rutina de mi jefe";
    public int Exercises = 5;
    public int Duration = 360;

    public Rutine(int id, String name, String Description, int Exercises, int Duration) {
        this.id = id;
        this.name = name;
        this.Description = Description;
        this.Exercises = Exercises;
        this.Duration = Duration;
    }

    public Rutine(String name, String Description, int Exercises, int Duration ) {
        this.name = name;
        this.Description = Description;
        this.Exercises = Exercises;
        this.Duration = Duration;
    }
}
