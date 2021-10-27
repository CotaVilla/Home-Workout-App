package com.example.homeworkoutapp.ui.exercises;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.Recyclers.ExercisesRecycler;
import com.example.homeworkoutapp.Recyclers.FilterRecycler;
import com.example.homeworkoutapp.Recyclers.RutinesRecycler;
import com.example.homeworkoutapp.databinding.FragmentExercisesBinding;
import com.example.homeworkoutapp.databinding.FragmentRoutinesBinding;
import com.example.homeworkoutapp.ui.routines.RoutinesViewModel;

import java.util.ArrayList;

public class ExercisesFragment extends Fragment {
    private Context context;

    private ExercisesViewModel exercisesViewModel;
    private FragmentExercisesBinding binding;

    ArrayList<String> exersices;
    ArrayList<String> filters;
    RecyclerView recycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exercisesViewModel =
                new ViewModelProvider(this).get(ExercisesViewModel.class);

        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // RecyclerView Exercises

        recycler = (RecyclerView) root.findViewById(R.id.filter_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        filters = new ArrayList<String>();

        for (int i =0; i<10;i++){
            filters.add("Filtro #" + i);
        }
        FilterRecycler adapter_filters = new FilterRecycler(filters);
        recycler.setAdapter(adapter_filters);

        // END RecyclerView

        // RecyclerView Exercises

        recycler = (RecyclerView) root.findViewById(R.id.exercises_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        exersices = new ArrayList<String>();

        for (int i =0; i<10;i++){
            exersices.add("Ejercicio #" + i);
        }
        ExercisesRecycler adapter_exercises = new ExercisesRecycler(exersices);
        recycler.setAdapter(adapter_exercises);

        // END RecyclerView

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}