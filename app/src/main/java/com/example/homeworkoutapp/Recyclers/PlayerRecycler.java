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

// clase adaptador para la lista de ejercicios del reproductor
public class PlayerRecycler extends RecyclerView.Adapter<PlayerRecycler.itemToPlay>{
    private ArrayList<Rutine_Exercise> list_exercises;
    private int selectedItemPos = -1;
    private int lastItemSelectedPos = -1;
    private Fragment currentFragment;


    //Constructor de la clase
    public PlayerRecycler(ArrayList<Rutine_Exercise> list_exercises,Fragment playerFragment){

        this.list_exercises = list_exercises;
        currentFragment = playerFragment;
    }

    //Inicializador de los items de la lista
    @NonNull
    @Override
    public itemToPlay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Aqui se le coloca el layout que va a utilizar el item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_exercise, parent, false);
        PlayerRecycler.itemToPlay rutineItem = new PlayerRecycler.itemToPlay(view);
        return rutineItem;
    }

    // funcion para obtener los datos del ejercicio seleccionado
    public Rutine_Exercise getSelectedValues(){
        Rutine_Exercise exercise= list_exercises.get(selectedItemPos);
        return exercise;
    }

    //Carga con los valores a los item de la lista
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

    //Obtiene el numero de items que debe de generar
    @Override
    public int getItemCount() {
        return list_exercises.size();
    }

    // Clase del item que se va a generar en al lista de ejercicios en el reproductor
    public class itemToPlay extends RecyclerView.ViewHolder {
        //variables de la clase
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

        //constructor de la clase
        public itemToPlay(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // gettin ids of objects in item
            container = itemView.findViewById(R.id.exercise_container);
            position = itemView.findViewById(R.id.exercise_position);
            ejercicio = itemView.findViewById(R.id.exercise_name);
            duration = itemView.findViewById(R.id.exercise_duration);

            //evento de clic sobre el item
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
            container.setBackgroundResource(R.drawable.container_item);
        }

        private void selecterBg() {
            container.setBackgroundResource(R.drawable.container_selected);
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
