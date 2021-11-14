package com.example.homeworkoutapp.objects;

public class Exercise {
    public int exercise_id;
    public int type_id;
    public int actual_type_id;
    public String name;
    public String description;
    public String gifLocation;
    public String tips;

    public Exercise(int exercise_id, int type_id, int actual_type_id, String name, String description, String gifLocation, String tips) {
        this.exercise_id = exercise_id;
        this.type_id = type_id;
        this.actual_type_id =actual_type_id;
        this.name = name;
        this.description = description;
        this.gifLocation = gifLocation;
        this.tips = tips;
    }
}
