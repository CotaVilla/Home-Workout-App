package com.example.homeworkoutapp.ui.routines;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.Recyclers.ExerciseRutinesRecycler;
import com.example.homeworkoutapp.StartActivity;
import com.example.homeworkoutapp.databinding.FragmentEditRoutineBinding;
import com.example.homeworkoutapp.objects.Rutine;
import com.example.homeworkoutapp.objects.Rutine_Exercise;
import com.example.homeworkoutapp.ui.exercises.NewExerciseFragment;

import java.util.ArrayList;

public class EditRoutineFragment extends Fragment {

    private Context context;
    private FragmentEditRoutineBinding binding;
    private StartActivity activity;
    Rutine_Exercise newRE;

    Rutine rutine;
    public ArrayList<Rutine_Exercise> exercises;
    RecyclerView recycler;
    Database_Helper database_helper;
    ExerciseRutinesRecycler adapter;

    TextView title;
    EditText name;
    EditText description;
    AppCompatButton accept;
    AppCompatButton cancel;
    TextView exercisesCount;
    LinearLayout add_exercise;

    public EditRoutineFragment (Rutine rutine){
        this.rutine = rutine;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (StartActivity) getActivity();
        context = getContext();
        database_helper = new Database_Helper(getActivity());

        exercises = database_helper.getExercises(rutine.id);
        adapter = new ExerciseRutinesRecycler(exercises);
    }

    @Override
    public void onStart() {
        super.onStart();
        exercisesCount.setText(exercises.size()+"/100");
    }

    @Override
    public void onResume() {
        super.onResume();
        newRE = activity.getPasableRE();
        int position = adapter.getEditExercisePosition();
        if(newRE != null && position == -1){
            newRE.position = exercises.size()+1;
            exercises.add(newRE);
            activity.setPasableRE(null);
            adapter.list_exercises = exercises;
            adapter.notifyDataSetChanged();
        }
        else if(newRE != null && position >= 0){
            exercises.set(adapter.getEditExercisePosition(),newRE);
            adapter.list_exercises = exercises;
            adapter.notifyItemChanged(adapter.getEditExercisePosition());
            adapter.setEditExercisePosition(-1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_routine, container, false);

        // RecyclerView Rutinas
        recycler = (RecyclerView) root.findViewById(R.id.rutine_exercises_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recycler.setAdapter(adapter);
        // END RecyclerView

        title = root.findViewById(R.id.frag_title);
        name = root.findViewById(R.id.rutine_name);
        description = root.findViewById(R.id.rutine_description);
        accept = root.findViewById(R.id.accept_add_rutine);
        cancel = root.findViewById(R.id.cancel_add_rutine);
        exercisesCount = root.findViewById(R.id.exercises_count);
        add_exercise = root.findViewById(R.id.add_excercise);

        title.setText("Editar rutina");
        name.setText(rutine.name);
        description.setText(rutine.Description);

        add_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exercises.size()<100)
                {
                    NewExerciseFragment newExerciseFragment = new NewExerciseFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_start,newExerciseFragment);
                    fragmentTransaction.commit();
                }
                else {
                    makeToast(Toast.makeText(context,"Ha llegado al limite de ejercicios.",Toast.LENGTH_SHORT));
                }
            }
        });


        accept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (validateDataInserted()){
                    editRutine();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Return to rutines
                RoutinesFragment routines = new RoutinesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Remove all saved fragments in backstack
                int count = fragmentManager.getBackStackEntryCount();
                for(int i = 0; i < count; ++i) {
                    fragmentManager.popBackStack();
                }

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_start,routines);
                fragmentTransaction.commit();
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                exercisesCount.setText(adapter.list_exercises.size()+"/100");
                super.onChanged();
            }
        });

        return root;
    }
    private boolean validateDataInserted(){
        String nameContent= name.getText().toString();
        String descriptionContent = description.getText().toString();

        if(nameContent.length() == 0){
            makeToast(Toast.makeText(context,"Ingrese el nombre de la rutina.",Toast.LENGTH_SHORT));
            return false;
        }
        if (descriptionContent.length() == 0){
            description.setText("Sin descripci??n.");
        }
        return true;
    }
    public void makeToast(Toast _toast){
        Toast toast = _toast;
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.container_toast);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }

    public void editRutine(){

        //  Get exercises of new rutine
        exercises = ((ExerciseRutinesRecycler)recycler.getAdapter()).list_exercises;

        //Get duration and exercise count
        String routine_name = name.getText().toString();
        String routine_description = description.getText().toString();
        int routine_duration = 0;
        int routine_exercises = exercises.size();

        for (Rutine_Exercise rutine: exercises) {
            routine_duration += (rutine.work_time+rutine.rest_time) * rutine.repeats;
        }

        rutine.name = routine_name;
        rutine.Description =routine_description;
        rutine.Exercises = routine_exercises;
        rutine.Duration =routine_duration;

        database_helper.updateRutine(rutine,exercises);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}