package com.example.homeworkoutapp.objects;

// Clase para guardar los datos de los ejercicios en las rutinas.
public class Rutine_Exercise {
    public int exercise_id =1;
    public int rutine_id = 1;
    public int position;
    public String exercise_name = "Ejercicio";
    public int reps;
    public int work_time = 30;
    public int rest_time = 15;
    public int repeats = 4;

    public Rutine_Exercise(int exercise_id, int rutine_id, int position, String exercise_name, int work_time, int rest_time, int repeats) {
        this.exercise_id = exercise_id;
        this.rutine_id = rutine_id;
        this.position = position;
        this.exercise_name = exercise_name;
        this.work_time = work_time;
        this.rest_time = rest_time;
        this.repeats = repeats;
    }


    public Rutine_Exercise(String exercise_name, int position) {
        this.exercise_name = exercise_name;
        this.position = position;
    }
}
