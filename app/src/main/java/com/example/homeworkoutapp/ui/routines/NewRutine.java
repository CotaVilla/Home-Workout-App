package com.example.homeworkoutapp.ui.routines;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.Recyclers.ExerciseRutinesRecycler;
import com.example.homeworkoutapp.Recyclers.RutinesRecycler;
import com.example.homeworkoutapp.databinding.FragmentNewRutineBinding;
import com.example.homeworkoutapp.databinding.FragmentRoutinesBinding;
import com.example.homeworkoutapp.objects.Rutine_Exercise;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewRutine#newInstance} factory method to
 * create an instance of this fragment.
 */

public class NewRutine extends Fragment {

    private Context context;
    private FragmentRoutinesBinding binding;

    // RecyclerView
    ArrayList<Rutine_Exercise> rutines;
    RecyclerView recycler;

    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewRutine() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewRutine newInstance(String param1, String param2) {
        NewRutine fragment = new NewRutine();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To save passed data
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_rutine, container, false);

        // RecyclerView Rutinas
        recycler = (RecyclerView) root.findViewById(R.id.rutine_exercises_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        rutines = new ArrayList<Rutine_Exercise>();

        for (int i =0; i<10;i++){
            rutines.add(new Rutine_Exercise("Exercise #" + i,i));
        }
        ExerciseRutinesRecycler adapter = new ExerciseRutinesRecycler(rutines);
        recycler.setAdapter(adapter);
        // END RecyclerView
        return root;
    }
}