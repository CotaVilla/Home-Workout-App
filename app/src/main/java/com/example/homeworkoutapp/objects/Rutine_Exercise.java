package com.example.homeworkoutapp.objects;

public class Rutine_Exercise {
    public int id =1;
    public int exercise_id =1;
    public int rutine_id = 1;
    public String rutine = "Rutina";
    public String exercise = "Ejercicio";
    public int reps;
    public int work_time = 30;
    public int rest_time = 15;
    public int repeats = 4;
    public int position;

    public Rutine_Exercise(int id, int exercise_id, int rutine_id, String rutine, String exercise, int work_time, int rest_time, int repeats) {
        this.id = id;
        this.exercise_id = exercise_id;
        this.rutine_id = rutine_id;
        this.rutine = rutine;
        this.exercise = exercise;
        this.work_time = work_time;
        this.rest_time = rest_time;
        this.repeats = repeats;
    }


    public Rutine_Exercise(String exercise, int position) {
        this.exercise = exercise;
        this.position = position;
    }
}
