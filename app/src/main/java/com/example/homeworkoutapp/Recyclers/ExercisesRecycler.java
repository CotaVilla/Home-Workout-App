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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.StartActivity;
import com.example.homeworkoutapp.objects.Exercise;
import com.example.homeworkoutapp.ui.exercises.ViewExerciseFragment;

import java.util.ArrayList;

// Clase adaptador para la lista de ejercicios cuando queremos ver los ejercicios que hay en la app
public class ExercisesRecycler extends RecyclerView.Adapter<ExercisesRecycler.itemExercise> {
    ArrayList<Exercise> list_exercises;
    Database_Helper databaseHelper;
    StartActivity activity;
    boolean selectable;
    long hiddenFilter;

    //Constructor de la clase
    public ExercisesRecycler(ArrayList<Exercise> list_exercises ,Database_Helper databaseHelper, boolean selectable, StartActivity activity) {
        this.selectable = selectable;
        this.list_exercises = list_exercises;
        this.databaseHelper = databaseHelper;
        hiddenFilter = databaseHelper.getHiddenFilterId();
        this.activity = activity;
    }

    //Inicializador de los items de la lista
    @NonNull
    @Override
    public ExercisesRecycler.itemExercise onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Aqui se le coloca el layout que va a utilizar el item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_excersice,null,false);
        itemExercise exercise = new itemExercise(view);
        return exercise;
    }

    //Carga con los valores a los item de la lista
    @Override
    public void onBindViewHolder(@NonNull itemExercise holder, int position) {
        Exercise object = list_exercises.get(position);
        holder.exercise = object;
        holder.name.setText(object.name);
        holder.excercise_description.setText(object.description);
    }

    //Obtiene el numero de items que debe de generar
    @Override
    public int getItemCount() {
        return list_exercises.size();
    }

    // Clase del item que se va a generar en al lista de ejercicios de la rutina
    public class itemExercise extends RecyclerView.ViewHolder {
        //Variables del item
        private Context context;

        Exercise exercise;
        // Objects in item exercise item
        ImageView options;
        TextView name;
        TextView excercise_description;

        //Contructor del item
        public itemExercise(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // Getting id's of objects in item
            options = itemView.findViewById(R.id.excercise_options);
            name = itemView.findViewById(R.id.excercise_name);
            excercise_description = itemView.findViewById(R.id.excercise_description);

            // cuando se carga esta lista para poder elegir el ejercicio al crear un nuevo ejercicio en la rutina
            if (selectable){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.setPasableExercise(exercise);
                        FragmentManager fragmentManager = ((FragmentActivity) unwrap(v.getContext())).getSupportFragmentManager();
                        fragmentManager.popBackStack();
                    }
                });
            }

            // eventos del item
            options.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","A click was made on item: "+ name);
                    showDialog();
                }

                // Show OptionsFragment menu of the rutine
                public void showDialog(){
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_options_excersice);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    TextView option_title = dialog.findViewById(R.id.excercise_options_title);
                    TextView option_open = dialog.findViewById(R.id.excercise_option_open);
                    TextView option_hide = dialog.findViewById(R.id.excercise_option_hide);

                    option_title.setText("Opciones > " + exercise.name);

                    // play click event
                    option_open.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("demo","Play: "+ exercise.name);
                            ViewExerciseFragment ViewExerciseFragment = new ViewExerciseFragment(exercise);
                            FragmentTransaction fragmentTransaction = ((FragmentActivity) unwrap(v.getContext())).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.replace(R.id.nav_host_fragment_content_start, ViewExerciseFragment);
                            fragmentTransaction.commit();
                            dialog.dismiss();
                        }

                    });
                    // edit click event
                    if((long)exercise.actual_type_id == hiddenFilter)
                    {
                        option_hide.setText("Desocultar");
                        option_hide.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("demo","Edit: "+ exercise.name);
                                databaseHelper.unhideExercise(exercise);
                                int pos = getAdapterPosition();
                                list_exercises.remove(pos);
                                notifyItemRemoved(pos);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    else {
                        option_hide.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("demo","Edit: "+ exercise.name);
                                int pos = getAdapterPosition();
                                list_exercises.remove(pos);
                                notifyItemRemoved(pos);
                                databaseHelper.hideExercise(exercise);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }


                }
            });
        }
    }
    // usada para cargar nuevos ejercicios en la lista cuando se le le da clic a un filtro
    public void changeExercises(ArrayList<Exercise> exercises){
        list_exercises = exercises;
    };

    // for unwrapping context in dialog
    private Context unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return context;
    }
}
