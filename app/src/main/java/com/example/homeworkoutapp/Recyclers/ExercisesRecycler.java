package com.example.homeworkoutapp.Recyclers;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.R;

import java.io.File;
import java.util.ArrayList;

public class ExercisesRecycler extends RecyclerView.Adapter<ExercisesRecycler.Exercise> {
    ArrayList<String> list_exercises;

    public ExercisesRecycler(ArrayList<String> list_exercises) {
        this.list_exercises = list_exercises;
    }

    @NonNull
    @Override
    public ExercisesRecycler.Exercise onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_excersice,null,false);
        Exercise exercise = new Exercise(view);
        return exercise;
    }

    @Override
    public void onBindViewHolder(@NonNull Exercise holder, int position) {
        String name = list_exercises.get(position);

        holder.name.setText(name);
        holder.filter = name;
        holder.excercise_description.setText("Ejemplo de descripcion.");


    }

    @Override
    public int getItemCount() {
        return list_exercises.size();
    }

    public class Exercise extends RecyclerView.ViewHolder {
        private Context context;

        // Objects in item exercise item
        ImageView options;
        TextView name;
        TextView excercise_description;
        String filter;

        public Exercise(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // Getting id's of objects in item
            options = itemView.findViewById(R.id.excercise_options);
            name = itemView.findViewById(R.id.excercise_name);
            excercise_description = itemView.findViewById(R.id.excercise_description);

            options.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","A click was made on item: "+ name);
                    showDialog();
                }

                // Show options menu of the rutine
                public void showDialog(){

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.options_excersice);

                    TextView option_title = dialog.findViewById(R.id.excercise_options_title);
                    TextView option_open = dialog.findViewById(R.id.excercise_option_open);
                    TextView option_hide = dialog.findViewById(R.id.excercise_option_hide);

                    option_title.setText("Opciones > " + filter);

                    // play click event
                    option_open.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Play: "+ filter);
                        }
                    });

                    // edit click event
                    option_hide.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Edit: "+ filter);
                        }
                    });

                    dialog.show();
                }
            });
        }
    }
}
