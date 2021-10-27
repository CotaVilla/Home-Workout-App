package com.example.homeworkoutapp.Recyclers;

import android.app.Dialog;
import android.content.Context;
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

public class RutinesRecycler extends RecyclerView.Adapter<RutinesRecycler.Rutine>{

    ArrayList<String> listRutines;

    // Constructor of the class
    public RutinesRecycler(ArrayList<String> listRutines){
        this.listRutines = listRutines;
    }

    // To create viewHolders (items)
    @NonNull
    @Override
    public Rutine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutine,null,false);
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
        private Context context;

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

            context = itemView.getContext();

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

            // Listen click on just the options
            options.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","A click was made on item: "+ name);
                    showDialog();
                }

                // Show options menu of the rutine
                public void showDialog(){

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.options_rutine);

                    TextView option_title = dialog.findViewById(R.id.rutine_options_title);
                    TextView option_play = dialog.findViewById(R.id.rutine_option_play);
                    TextView option_edit = dialog.findViewById(R.id.rutine_option_edit);
                    TextView option_duplicate = dialog.findViewById(R.id.rutine_option_duplicate);
                    TextView option_delete = dialog.findViewById(R.id.rutine_option_delete);

                    option_title.setText("Opciones > " + name);

                    // play click event
                    option_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Play: "+ name);
                        }
                    });

                    // edit click event
                    option_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Edit: "+ name);
                        }
                    });

                    // duplicate click event
                    option_duplicate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Duplicate: "+ name);
                        }
                    });

                    // option_delete click event
                    option_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Delete: "+ name);
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}
