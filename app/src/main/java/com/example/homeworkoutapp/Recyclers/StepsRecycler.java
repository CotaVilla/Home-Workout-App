package com.example.homeworkoutapp.Recyclers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.objects.Rutine;
import com.example.homeworkoutapp.objects.Step;
import com.example.homeworkoutapp.ui.play.PlayFragment;
import com.example.homeworkoutapp.ui.routines.EditRoutine;
import com.example.homeworkoutapp.ui.routines.RoutinesFragment;

import java.util.ArrayList;

// Clase para adaptar los datos al RecyclerView de rutinas https://www.youtube.com/watch?v=fnavhYGqZD4
// Video de como hacer botones https://www.youtube.com/watch?v=FA5cGLLiSWs
// Menu con opciones para los items https://www.youtube.com/watch?v=fNpt-_JHS64

public class StepsRecycler extends RecyclerView.Adapter<StepsRecycler.Step_item>{

    ArrayList<Step> step_list;

    // Constructor of the class
    public StepsRecycler(ArrayList<Step> step_list){
        this.step_list = step_list;
    }

    @NonNull
    @Override
    public Step_item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step,null,false);
        Step_item stepItem = new Step_item(view);
        return stepItem;
    }

    @Override
    public void onBindViewHolder(@NonNull Step_item holder, int position) {
        Step object = step_list.get(position);
        holder.step = object;
        holder.step_number.setText("Paso #"+ (position+1));
        holder.step_content.setText(holder.step.step_description);
    }

    // Get the amount of items to create
    @Override
    public int getItemCount() {
        return step_list.size();
    }

    // Referencia a la estructura del elemento
    public class Step_item extends RecyclerView.ViewHolder {
        Step step;

        // Objects in item rutine
        TextView step_number;
        TextView step_content;

        // Aqui
        public Step_item(@NonNull View itemView) {
            super(itemView);

            step_number = itemView.findViewById(R.id.step_number);
            step_content = itemView.findViewById(R.id.step_content);
        }
    }

}
