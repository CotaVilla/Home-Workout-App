package com.example.homeworkoutapp.ui.routines;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.databinding.FragmentRoutinesBinding;
import com.example.homeworkoutapp.Recyclers.RutinesRecycler;
import com.example.homeworkoutapp.objects.Rutine;
import com.example.homeworkoutapp.objects.Rutine_Exercise;
import com.example.homeworkoutapp.ui.play.PlayFragment;

import java.util.ArrayList;

public class RoutinesFragment extends Fragment {

    private Context context;
    private RoutinesViewModel routinesViewModel;
    private FragmentRoutinesBinding binding;


    // RecyclerView
    ArrayList<Rutine> rutines;
    RecyclerView recycler;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        routinesViewModel = new ViewModelProvider(this).get(RoutinesViewModel.class);

        binding = FragmentRoutinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // RecyclerView Rutinas
        recycler = (RecyclerView) root.findViewById(R.id.rutines_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        // Get routines from database
        Database_Helper database_helper = new Database_Helper(getActivity());
        rutines = database_helper.getRutines();


        RutinesRecycler adapter = new RutinesRecycler(rutines);
        recycler.setAdapter(adapter);

        // END RecyclerView


        return root;
        /*binding = FragmentRoutinesBinding.inflate(getLayoutInflater());
        return  binding.getRoot();*/
    }

    @Override
    public  void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        binding.addRutine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                NewRutine newRutine = new NewRutine();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_start, newRutine);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of RutinesFragment");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of RutinesFragment");
        super.onPause();
    }

    //To avoid memory leakage we use the on Destroy
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}