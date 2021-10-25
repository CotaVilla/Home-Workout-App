package com.example.homeworkoutapp.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.R;

import java.util.ArrayList;

// Clase para adaptar los datos al RecyclerView de rutinas https://www.youtube.com/watch?v=fnavhYGqZD4
// Video de como hacer botones https://www.youtube.com/watch?v=FA5cGLLiSWs
// Menu con opciones para los items https://www.youtube.com/watch?v=fNpt-_JHS64

public class RutineList extends RecyclerView.Adapter<RutineList.Rutine>{


    ArrayList<String> listRutines;

    // Constructor of the class
    public RutineList(ArrayList<String> listRutines){
        this.listRutines = listRutines;
    }

    // To create viewHolders (items)
    @NonNull
    @Override
    public Rutine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rutine,null,false);
        Rutine rutine = new Rutine(view);
        return rutine;
    }

    // Where we get the data on the item
    @Override
    public void onBindViewHolder(@NonNull Rutine holder, int position) {
        String name = listRutines.get(position);
        holder.rutine_name.setText(name);
        holder.name = name;
    }

    // Get the amount of items to create
    @Override
    public int getItemCount() {
        return listRutines.size();
    }

    // Referencia a la estructura del elemento
    public class Rutine extends RecyclerView.ViewHolder {
        // Objects in item rutine
        TextView rutine_name;
        TextView description;
        TextView exercises;
        TextView time;
        ImageView options;
        String name;

        // Aqui
        public Rutine(@NonNull View itemView) {
            super(itemView);

            // gettin ids of objects in item
            rutine_name = itemView.findViewById(R.id.rutine_name);
            description = itemView.findViewById(R.id.rutine_description);
            exercises = itemView.findViewById(R.id.rutine_exercises);
            time = itemView.findViewById(R.id.rutine_duration);
            options = itemView.findViewById(R.id.btn_options);


            // Listen click on all the item
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","onClick: al item se le hizo clic");
                }
            });

            // Listen clic on just the options
            options.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","A click was made on item: "+ name);
                }
            });
        }
    }
}
