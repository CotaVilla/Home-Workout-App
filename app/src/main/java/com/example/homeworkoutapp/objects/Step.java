package com.example.homeworkoutapp.objects;

// Clase para guardar los datos de los pasos para los detalles de un ejercicio
public class Step {
    public int step_id;
    public int exercise_id;
    public String step_description;

    public Step(int step_id, int exercise_id, String step_description) {
        this.step_id = step_id;
        this.exercise_id = exercise_id;
        this.step_description = step_description;
    }
}
