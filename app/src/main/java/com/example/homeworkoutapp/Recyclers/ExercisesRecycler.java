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
import com.example.homeworkoutapp.objects.Exercise;

import java.io.File;
import java.util.ArrayList;

public class ExercisesRecycler extends RecyclerView.Adapter<ExercisesRecycler.itemExercise> {
    ArrayList<Exercise> list_exercises;

    public ExercisesRecycler(ArrayList<Exercise> list_exercises) {
        this.list_exercises = list_exercises;
    }

    @NonNull
    @Override
    public ExercisesRecycler.itemExercise onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_excersice,null,false);
        itemExercise exercise = new itemExercise(view);
        return exercise;
    }

    @Override
    public void onBindViewHolder(@NonNull itemExercise holder, int position) {
        Exercise object = list_exercises.get(position);
        holder.exercise = object;
        holder.name.setText(object.name);
        holder.excercise_description.setText(object.description);
    }

    @Override
    public int getItemCount() {
        return list_exercises.size();
    }

    public class itemExercise extends RecyclerView.ViewHolder {
        private Context context;

        Exercise exercise;
        // Objects in item exercise item
        ImageView options;
        TextView name;
        TextView excercise_description;

        public itemExercise(@NonNull View itemView) {
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

                    option_title.setText("Opciones > " + exercise.name);

                    // play click event
                    option_open.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Play: "+ exercise.name);
                        }
                    });

                    // edit click event
                    option_hide.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Edit: "+ exercise.name);
                        }
                    });

                    dialog.show();
                }
            });
        }
    }
    public void changeExercises(ArrayList<Exercise> exercises){
        list_exercises = exercises;
    };
}
