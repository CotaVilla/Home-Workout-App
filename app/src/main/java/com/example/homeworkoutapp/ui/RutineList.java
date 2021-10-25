package com.example.homeworkoutapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.R;

import java.util.ArrayList;

// Clase para adaptar los datos al RecyclerView de rutinas https://www.youtube.com/watch?v=fnavhYGqZD4

public class RutineList extends RecyclerView.Adapter<RutineList.Rutine>{


    ArrayList<String> listRutines;

    // Constructor de la clase
    public RutineList(ArrayList<String> listRutines){
        this.listRutines = listRutines;
    }

    @NonNull
    @Override
    public Rutine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_rutine,null,false);
        return new Rutine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Rutine holder, int position) {
        holder.asignData(listRutines.get(position));
    }

    @Override
    public int getItemCount() {
        return listRutines.size();
    }

    // Referencia a la estructura del elemento
    public class Rutine extends RecyclerView.ViewHolder {
        TextView rutine_name;
        TextView description;
        TextView exercises;

        public Rutine(@NonNull View itemView) {
            super(itemView);
            rutine_name = itemView.findViewById(R.id.rutine_name);
        }

        public void asignData(String name) {
            rutine_name.setText(name);
        }

    }
}
