package com.example.homeworkoutapp.Recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.objects.Rutine_Exercise;
import com.example.homeworkoutapp.ui.player.PlayerFragment;

import java.util.ArrayList;

public class PlayerRecycler extends RecyclerView.Adapter<PlayerRecycler.itemToPlay>{
    private ArrayList<Rutine_Exercise> list_exercises;
    private int selectedItemPos = -1;
    private int lastItemSelectedPos = -1;
    private Fragment currentFragment;

    public PlayerRecycler(ArrayList<Rutine_Exercise> list_exercises,Fragment playerFragment){
        this.list_exercises = list_exercises;
        currentFragment = playerFragment;
    }

    @NonNull
    @Override
    public itemToPlay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_exercise, parent, false);
        PlayerRecycler.itemToPlay rutineItem = new PlayerRecycler.itemToPlay(view);
        return rutineItem;
    }

    public Rutine_Exercise getSelectedValues(){
        Rutine_Exercise exercise= list_exercises.get(selectedItemPos);
        return exercise;
    }

    @Override
    public void onBindViewHolder(@NonNull itemToPlay holder, @SuppressLint("RecyclerView") int position) {
        Rutine_Exercise object = list_exercises.get(position);
        holder.exercise = object;

        holder.selected = false;
        holder.actualRep = 1;
        holder.actualRest = 1;
        holder.time = segToTime((object.rest_time+ object.work_time)*object.repeats);

        holder.position.setText("#" + object.position);
        holder.ejercicio.setText(object.exercise_name);
        holder.duration.setText("Duration: " + holder.time);
        if(selectedItemPos == -1 ){
            selectedItemPos = position;
        }
        if(position == selectedItemPos)
            holder.selecterBg();
        else
            holder.defaultBg();
    }

    @Override
    public int getItemCount() {
        return list_exercises.size();
    }

    public class itemToPlay extends RecyclerView.ViewHolder {
        private Context context;

        Rutine_Exercise exercise;

        Boolean selected;
        int actualRep;
        int actualRest;
        String time;

        // Objects in item rutine
        TextView position;
        TextView ejercicio;
        TextView duration;
        LinearLayout container;

        public itemToPlay(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // gettin ids of objects in item
            container = itemView.findViewById(R.id.exercise_container);
            position = itemView.findViewById(R.id.exercise_position);
            ejercicio = itemView.findViewById(R.id.exercise_name);
            duration = itemView.findViewById(R.id.exercise_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: cambiar ejercicio al seleccionar el ejercicio
                    lastItemSelectedPos = selectedItemPos;
                    notifyItemChanged(lastItemSelectedPos);
                    selectedItemPos = getAdapterPosition();
                    notifyItemChanged(selectedItemPos);
                    if (currentFragment instanceof PlayerFragment) {
                        ((PlayerFragment) currentFragment).selectedItemChanged();
                    }
                }
            });
        }



        private void defaultBg() {
            container.setBackgroundResource(R.drawable.round_container_blue);
        }

        private void selecterBg() {
            container.setBackgroundResource(R.drawable.background_selected);
        }
    }

    public void changeSelectedNext(){
        if(selectedItemPos<(getItemCount()-1)){
            lastItemSelectedPos= selectedItemPos;
            selectedItemPos++;
            notifyItemChanged(selectedItemPos);
            notifyItemChanged(lastItemSelectedPos);
        }
    }

    public void changeSelectedPrevious(){
        if(selectedItemPos>0){
            lastItemSelectedPos = selectedItemPos;
            selectedItemPos--;
            notifyItemChanged(selectedItemPos);
            notifyItemChanged(lastItemSelectedPos);
        }
    }

    public void resetSelection(){
        lastItemSelectedPos = selectedItemPos;
        selectedItemPos=0;
        notifyItemChanged(selectedItemPos);
        notifyItemChanged(lastItemSelectedPos);
    }

    private String segToTime(int totalSecs){
        String time;

        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }
}
