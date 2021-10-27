package com.example.homeworkoutapp.ui.routines;

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
import com.example.homeworkoutapp.databinding.FragmentRoutinesBinding;
import com.example.homeworkoutapp.Recyclers.RutinesRecycler;

import java.util.ArrayList;

public class RoutinesFragment extends Fragment {

    private Context context;
    private RoutinesViewModel routinesViewModel;
    private FragmentRoutinesBinding binding;

    // RecyclerView
    ArrayList<String> rutines;
    RecyclerView recycler;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        routinesViewModel = new ViewModelProvider(this).get(RoutinesViewModel.class);

        binding = FragmentRoutinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // RecyclerView Rutinas

        recycler = (RecyclerView) root.findViewById(R.id.rutines_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        rutines = new ArrayList<String>();

        for (int i =0; i<10;i++){
            rutines.add("Rutina #" + i);
        }
        RutinesRecycler adapter = new RutinesRecycler(rutines);
        recycler.setAdapter(adapter);

        // END RecyclerView


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}