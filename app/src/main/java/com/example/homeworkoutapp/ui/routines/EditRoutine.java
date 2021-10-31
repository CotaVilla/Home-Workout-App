package com.example.homeworkoutapp.ui.routines;

import android.content.Context;
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

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.Recyclers.ExerciseRutinesRecycler;
import com.example.homeworkoutapp.databinding.FragmentRoutinesBinding;
import com.example.homeworkoutapp.objects.Rutine;
import com.example.homeworkoutapp.objects.Rutine_Exercise;

import java.util.ArrayList;

public class EditRoutine extends Fragment {

    private Context context;
    private FragmentRoutinesBinding binding;

    public ArrayList<Rutine_Exercise> exercises;
    public Rutine rutine;
    RecyclerView recycler;

    Database_Helper database_helper;

    public EditRoutine(Rutine rutine) {
        this.rutine = rutine;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_routine, container, false);
        database_helper = new Database_Helper(getActivity());
        exercises = database_helper.getExercises(rutine.id);

        // RecyclerView Rutinas
        recycler = (RecyclerView) root.findViewById(R.id.rutine_exercises_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        ExerciseRutinesRecycler adapter = new ExerciseRutinesRecycler(exercises);
        recycler.setAdapter(adapter);
        // END RecyclerView

        EditText name = root.findViewById(R.id.rutine_name);
        EditText description = root.findViewById(R.id.rutine_description);
        AppCompatButton accept = root.findViewById(R.id.accept_add_rutine);
        AppCompatButton cancel = root.findViewById(R.id.cancel_add_rutine);

        name.setText(rutine.name);
        description.setText(rutine.Description);

        accept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SaveRutine();
            }

            public void SaveRutine(){
                //  Get exercises of new rutine
                exercises = ((ExerciseRutinesRecycler)recycler.getAdapter()).list_exercises;

                //Get duration and exercise count
                int rutine_id;
                String routine_name = name.getText().toString();
                String routine_description = description.getText().toString();
                int routine_duration=0;
                int routine_exercises = exercises.size();

                for (Rutine_Exercise rutine: exercises) {
                    routine_duration += (rutine.work_time+rutine.rest_time) * rutine.repeats;
                }

                // Create routine
                Rutine newRutine= new Rutine(routine_name,routine_description,routine_exercises,routine_duration);

                // Insert routine in database
                long id = database_helper.insertRutine(newRutine); //Returns id

                //TODO: Add exercises to database to corresponding routine

                //Return to routines fragment
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

        return root;
    }
}