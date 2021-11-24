package com.example.homeworkoutapp.ui.exercises;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.Recyclers.ExercisesRecycler;
import com.example.homeworkoutapp.Recyclers.FilterRecycler;
import com.example.homeworkoutapp.StartActivity;
import com.example.homeworkoutapp.databinding.FragmentExercisesBinding;
import com.example.homeworkoutapp.objects.Exercise;
import com.example.homeworkoutapp.objects.Filter;

import java.util.ArrayList;

// clase para el fragmento donde se ve la lista de ejercicios
// se usa para seleccionar los ejercicios o solo para verlos
public class ExercisesFragment extends Fragment {
    //variables de la clase
    private Context context;
    private FragmentExercisesBinding binding;
    StartActivity activity;
    private Fragment currentFragment;

    private boolean selectable;

    Database_Helper database_helper;
    FilterRecycler adapter_filters;
    ExercisesRecycler adapter_exercises;

    TextView title;
    RecyclerView recycler;
    LinearLayout no_exercises;
    TextView selected_filter;
    EditText name_filter;

    ArrayList<Exercise> exersices;
    ArrayList<Filter> filters;
    int filter_id;
    String filter_description;
    String name_search;

    //Constructor de la clase
    public ExercisesFragment(boolean selectable){
        this.selectable = selectable;
    }

    // Inicializador de la vista
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity= (StartActivity)getActivity();
        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        filter_id = 0;
        filter_description = "Seleccionado: Todos";
        name_search = null;

        name_filter = root.findViewById(R.id.excercise_name);
        selected_filter = root.findViewById(R.id.selected_filter);
        selected_filter.setText(filter_description);
        title = root.findViewById(R.id.frag_title);
        if(selectable){
            title.setText("Selecionar ejercicio");
        }
        else {
            title.setText("Ejercicios");
        }

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

        exersices = database_helper.getFilteredExcercises(0,name_search);
        adapter_exercises = new ExercisesRecycler(exersices,database_helper, selectable, activity);
        recycler.setAdapter(adapter_exercises);

        // END RecyclerView

        // eventos para el buscador por nombre
        name_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name_search = name_filter.getText().toString();
                search_exercises();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }

    // metodo para cambiar los datos del filtro seleccionado
    public void setFilter(int filter_id, String description) {
        filter_description = description;
        this.filter_id = filter_id;

        selected_filter.setText("Seleccionado: " + filter_description);

        search_exercises();
    }

    // metodo par cargar los ejercicios aplicando los filtros
    public void search_exercises(){
        exersices = database_helper.getFilteredExcercises(filter_id,name_search);

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

    //para liberar memoria
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}