package com.example.homeworkoutapp.objects;

//Clase para guardar los datos de los filtros de los ejercicios.
public class Filter {
    public int filter_id;
    public String description;
    public String image_location;

    public Filter(int filter_id, String description, String image_location) {
        this.filter_id = filter_id;
        this.description = description;
        this.image_location = image_location;
    }
}
