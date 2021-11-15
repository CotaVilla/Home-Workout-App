package com.example.homeworkoutapp.ui.exercises;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.Recyclers.FilterRecycler;
import com.example.homeworkoutapp.Recyclers.StepsRecycler;
import com.example.homeworkoutapp.databinding.FragmentExercisesBinding;
import com.example.homeworkoutapp.databinding.FragmentViewExerciseBinding;
import com.example.homeworkoutapp.objects.Exercise;
import com.example.homeworkoutapp.objects.Step;
import com.example.homeworkoutapp.ui.routines.RoutinesFragment;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class ViewExerciseFragment extends Fragment {
    Exercise exercise;
    FragmentViewExerciseBinding binding;
    StepsRecycler stepsAdapterRecycler;
    Database_Helper database_helper;
    Context context;

    TextView exercise_Name;
    TextView exercise_Description;
    TextView exercise_Tips;
    GifImageView exercise_Video;
    RecyclerView steps_Recycler;


    ArrayList<Step> list_steps;

    public ViewExerciseFragment(Exercise exercise) {
        this.exercise = exercise;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();

        exercise_Name = root.findViewById(R.id.exercise_name_detail);
        exercise_Description = root.findViewById(R.id.excercise_description);
        exercise_Video = root.findViewById(R.id.gif_image_view);
        steps_Recycler = root.findViewById(R.id.recycler_steps);
        exercise_Tips = root.findViewById(R.id.exercise_tips);

        database_helper = new Database_Helper(getActivity());
        list_steps = database_helper.getSteps(exercise);

        exercise_Name.setText(exercise.name);
        exercise_Description.setText(exercise.description);
        exercise_Tips.setText(exercise.tips);

        exercise_Video.setImageResource(getMyDrawable(exercise.gifLocation));

        steps_Recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        stepsAdapterRecycler = new StepsRecycler(list_steps);
        steps_Recycler.setAdapter(stepsAdapterRecycler);

        return root;
    }

    private int getMyDrawable(String ImageName) {
        int image=0;

        image = context.getResources().getIdentifier(ImageName, "drawable", context.getPackageName());

        if (image ==0){
            image = context.getResources().getIdentifier("gif_homer", "drawable", context.getPackageName());
        }
        return image;
    }
}