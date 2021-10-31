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
import com.example.homeworkoutapp.objects.Rutine_Exercise;

import java.util.ArrayList;
import java.util.Collections;

public class ExerciseRutinesRecycler extends RecyclerView.Adapter<ExerciseRutinesRecycler.Exercise>{
    public ArrayList<Rutine_Exercise> list_exercises;

    public ExerciseRutinesRecycler(ArrayList<Rutine_Exercise> list_exercises) {
        this.list_exercises = list_exercises;
    }

    @NonNull
    @Override
    public ExerciseRutinesRecycler.Exercise onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutine_exercise,parent,false);
        ExerciseRutinesRecycler.Exercise exercise = new ExerciseRutinesRecycler.Exercise(view);
        return exercise;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseRutinesRecycler.Exercise holder, int position) {
        Rutine_Exercise object = list_exercises.get(position);

        holder.rutine_exercise = object;
        holder.excercise_name.setText(object.exercise_name);
        holder.workout_time.setText("Ejercicio: " + object.work_time + " sec");
        holder.rest_time.setText("Descanso: " + object.rest_time + " sec");
        holder.repeats.setText("Repeticiones: " + object.repeats + " sec");
        holder.duration.setText("Duracion: " + (object.work_time+object.rest_time)*object.repeats + " sec");
    }

    @Override
    public int getItemCount() {
        return list_exercises.size();
    }

    public class Exercise extends RecyclerView.ViewHolder {
        private Context context;

        Rutine_Exercise rutine_exercise;

        // Objects in item exercise item
        ImageView btn_options;
        TextView excercise_name;
        TextView workout_time;
        TextView rest_time;
        TextView repeats;
        TextView duration;
        ImageView btn_up;
        ImageView btn_down;

        public Exercise(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // Getting id's of objects in item
            btn_options = itemView.findViewById(R.id.btn_options);
            excercise_name = itemView.findViewById(R.id.excercise_name);
            workout_time = itemView.findViewById(R.id.workout_time);
            rest_time = itemView.findViewById(R.id.rest_time);
            repeats = itemView.findViewById(R.id.repeats);
            duration = itemView.findViewById(R.id.duration);
            btn_up = itemView.findViewById(R.id.btn_up);
            btn_down = itemView.findViewById(R.id.btn_down);

            btn_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rutine_exercise.position != 0){
                        int actual =rutine_exercise.position;
                        list_exercises.get(actual).position=actual-1;
                        list_exercises.get(actual-1).position=actual;
                        Collections.swap(list_exercises,actual,actual-1);
                        notifyItemMoved(actual,actual-1);
                    }
                }
            });

            btn_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rutine_exercise.position != (list_exercises.size()-1)){
                        int actual =rutine_exercise.position;
                        list_exercises.get(actual).position=actual+1;
                        list_exercises.get(actual+1).position=actual;
                        Collections.swap(list_exercises,actual,actual+1);
                        notifyItemMoved(actual,actual+1);
                    }
                }
            });

            btn_options.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","Options of: "+ rutine_exercise.exercise_name);
                    showDialog();
                }

                // Show options menu of the rutine_exercise
                public void showDialog(){

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.options_rutine_excersice);

                    TextView option_title = dialog.findViewById(R.id.rutine_options_title);
                    TextView option_edit = dialog.findViewById(R.id.rutine_option_edit);
                    TextView option_duplicate = dialog.findViewById(R.id.rutine_option_duplicate);
                    TextView option_delete = dialog.findViewById(R.id.rutine_option_delete);

                    option_title.setText(rutine_exercise.exercise_name + " > Opciones");

                    // edit click event
                    option_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Edit");
                        }
                    });

                    // duplicate click event
                    option_duplicate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Duplicate");
                        }
                    });

                    // delete click event
                    option_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Delete");
                        }
                    });

                    dialog.show();
                }
            });
        }
    }
}
