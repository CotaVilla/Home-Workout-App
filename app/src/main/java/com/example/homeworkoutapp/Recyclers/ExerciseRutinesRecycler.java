package com.example.homeworkoutapp.Recyclers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.objects.Rutine_Exercise;
import com.example.homeworkoutapp.ui.exercises.EditExerciseFragment;

import java.util.ArrayList;
import java.util.Collections;

// Clase adaptador para la lista de ejercicios de las rutinas
public class ExerciseRutinesRecycler extends RecyclerView.Adapter<ExerciseRutinesRecycler.itemExercise>{
    public ArrayList<Rutine_Exercise> list_exercises;

    int editExercisePosition = -1;

    //Se llama desde EditRutine o NewRutine al cargar un nuevo ejercicio
    public void setEditExercisePosition(int editExercisePosition) {
        this.editExercisePosition = editExercisePosition;
    }
    public int getEditExercisePosition() {
        return editExercisePosition;
    }

    //Constructor que recibe los ejercicios
    public ExerciseRutinesRecycler(ArrayList<Rutine_Exercise> list_exercises) {
        this.list_exercises = list_exercises;
    }


    // Inicializa cada item de la lista
    @NonNull
    @Override
    public itemExercise onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Aqui se le coloca el layout que va a utilizar el item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutine_exercise,parent,false);
        itemExercise exercise = new itemExercise(view);
        return exercise;
    }

    //Carga con los valores a los item de la lista
    @Override
    public void onBindViewHolder(@NonNull itemExercise holder, int position) {
        Rutine_Exercise object = list_exercises.get(position);

        holder.rutine_exercise = object;
        holder.excercise_name.setText("#" + (position+1) + " " + object.exercise_name);
        holder.workout_time.setText("Ejercicio: " + segToTime(object.rest_time));
        holder.rest_time.setText("Descanso: " + segToTime(object.rest_time));
        holder.repeats.setText("Repeticiones: " + object.repeats + " sec");
        holder.duration.setText("Duración: " + segToTime((object.work_time+object.rest_time)*object.repeats));
    }

    //Obtiene el numero de items que debe de generar
    @Override
    public int getItemCount() {
        return list_exercises.size();
    }

    // Clase del item que se va a generar en al lista de ejercicios de la rutina
    public class itemExercise extends RecyclerView.ViewHolder {
        //Variables
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

        // Constructor del item
        public itemExercise(@NonNull View itemView) {
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

            //Eventos
            btn_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clicked = getAdapterPosition();
                    if (clicked != 0){
                        list_exercises.get(clicked).position -= 1;
                        list_exercises.get(clicked-1).position += 1;

                        Collections.swap(list_exercises,clicked,clicked-1);
                        notifyItemMoved(clicked,clicked-1);
                        notifyItemChanged(clicked);
                        notifyItemChanged(clicked-1);
                    }
                }
            });

            btn_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clicked = getAdapterPosition();
                    if (clicked < getItemCount()-1){
                        list_exercises.get(clicked).position += 1;
                        list_exercises.get(clicked+1).position -= 1;

                        Collections.swap(list_exercises,clicked,clicked+1);
                        notifyItemMoved(clicked,clicked+1);
                        notifyItemChanged(clicked);
                        notifyItemChanged(clicked+1);
                    }
                }
            });

            btn_options.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","Options of: "+ rutine_exercise.exercise_name);
                    showDialog();
                }

                // Show OptionsFragment menu of the rutine_exercise
                public void showDialog(){

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_options_rutine_excersice);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
                            editExercisePosition = getAdapterPosition();
                            EditExerciseFragment playFragment = new EditExerciseFragment(rutine_exercise);
                            FragmentTransaction fragmentTransaction = ((FragmentActivity) unwrap(v.getContext())).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.replace(R.id.nav_host_fragment_content_start, playFragment);
                            fragmentTransaction.commit();
                            dialog.dismiss();
                        }
                    });

                    // duplicate click event
                    option_duplicate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Duplicate");
                            Rutine_Exercise exercise = list_exercises.get(getAdapterPosition());
                            Rutine_Exercise newDuplicate = new Rutine_Exercise(exercise.exercise_id,exercise.rutine_id,exercise.position,exercise.exercise_name,exercise.work_time,exercise.rest_time,exercise.repeats);
                            list_exercises.add(newDuplicate);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                    // delete click event
                    option_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Delete");
                            list_exercises.remove(getAdapterPosition());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    // for unwrapping context in dialog
    // porque por alguna razon cunado usas un dialogo el contexto esta envuelto en algo
    private Context unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return context;
    }

    // metodo para darle formato al tiempo
    private String segToTime(int totalSecs){
        String time;

        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }
}
