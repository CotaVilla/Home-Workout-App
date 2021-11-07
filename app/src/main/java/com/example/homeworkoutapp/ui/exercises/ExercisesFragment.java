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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.Recyclers.ExercisesRecycler;
import com.example.homeworkoutapp.Recyclers.FilterRecycler;
import com.example.homeworkoutapp.Recyclers.RutinesRecycler;
import com.example.homeworkoutapp.databinding.FragmentExercisesBinding;
import com.example.homeworkoutapp.databinding.FragmentRoutinesBinding;
import com.example.homeworkoutapp.objects.Exercise;
import com.example.homeworkoutapp.objects.Filter;
import com.example.homeworkoutapp.ui.routines.RoutinesViewModel;

import java.util.ArrayList;

public class ExercisesFragment extends Fragment {
    private Context context;

    private ExercisesViewModel exercisesViewModel;
    private FragmentExercisesBinding binding;
    private Fragment currentFragment;
    Database_Helper database_helper;
    FilterRecycler adapter_filters;
    ExercisesRecycler adapter_exercises;


    ArrayList<Exercise> exersices;
    ArrayList<Filter> filters;
    RecyclerView recycler;
    LinearLayout no_exercises;
    TextView selected_filter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selected_filter = getActivity().findViewById(R.id.selected_filter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exercisesViewModel =
                new ViewModelProvider(this).get(ExercisesViewModel.class);

        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // RecyclerView Exercises
        no_exercises = root.findViewById(R.id.no_excercises);
        recycler = (RecyclerView) root.findViewById(R.id.filter_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        database_helper = new Database_Helper(getActivity());
        filters = database_helper.getFilters();
        adapter_filters = new FilterRecycler(filters, this);
        recycler.setAdapter(adapter_filters);
        // END RecyclerView

        // RecyclerView Exercises
        recycler = (RecyclerView) root.findViewById(R.id.exercises_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        exersices = database_helper.get_excercises(0,null);
        adapter_exercises = new ExercisesRecycler(exersices);
        recycler.setAdapter(adapter_exercises);

        // END RecyclerView

        return root;
    }

    public void setFilter(String description, int filter_id){
        selected_filter.setText("Seleccionado: " + description);

        exersices = database_helper.get_excercises(filter_id,null);

        if(exersices.isEmpty()){
            recycler.setVisibility(View.GONE);
            no_exercises.setVisibility(View.VISIBLE);
        }
        else {
            recycler.setVisibility(View.VISIBLE);
            no_exercises.setVisibility(View.GONE);
            adapter_exercises.changeExercises(exersices);
            adapter_exercises.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}