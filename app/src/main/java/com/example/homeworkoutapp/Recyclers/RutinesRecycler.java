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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.objects.Rutine;
import com.example.homeworkoutapp.ui.play.PlayFragment;
import com.example.homeworkoutapp.ui.routines.EditRoutine;
import com.example.homeworkoutapp.ui.routines.NewRutine;

import java.util.ArrayList;

// Clase para adaptar los datos al RecyclerView de rutinas https://www.youtube.com/watch?v=fnavhYGqZD4
// Video de como hacer botones https://www.youtube.com/watch?v=FA5cGLLiSWs
// Menu con opciones para los items https://www.youtube.com/watch?v=fNpt-_JHS64

public class RutinesRecycler extends RecyclerView.Adapter<RutinesRecycler.Rutine_item>{

    ArrayList<Rutine> listRutines;

    // Constructor of the class
    public RutinesRecycler(ArrayList<Rutine> listRutines){
        this.listRutines = listRutines;
    }

    // To create viewHolders (items)
    @NonNull
    @Override
    public Rutine_item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutine,null,false);
        Rutine_item rutineItem = new Rutine_item(view);
        return rutineItem;
    }

    // Where we get the data on the item
    @Override
    public void onBindViewHolder(@NonNull Rutine_item holder, int position) {
        Rutine object = listRutines.get(position);
        holder.rutine = object;
        holder.rutine_name.setText(object.name);
        holder.description.setText(object.Description);
        holder.exercises.setText("Ejercicio: " + object.Exercises);
        holder.time.setText("Duration: " + object.Duration + " sec");
    }

    // Get the amount of items to create
    @Override
    public int getItemCount() {
        return listRutines.size();
    }

    // Referencia a la estructura del elemento
    public class Rutine_item extends RecyclerView.ViewHolder {
        private Context context;

        Rutine rutine;

        // Objects in item rutine
        TextView rutine_name;
        TextView description;
        TextView exercises;
        TextView time;
        ImageView options;

        // Aqui
        public Rutine_item(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // gettin ids of objects in item
            rutine_name = itemView.findViewById(R.id.rutine_name);
            description = itemView.findViewById(R.id.rutine_description);
            exercises = itemView.findViewById(R.id.rutine_exercises);
            time = itemView.findViewById(R.id.rutine_duration);
            options = itemView.findViewById(R.id.btn_options);


            // Listen click on just the options
            options.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","A click was made on item: "+ rutine.name);
                    showDialog();
                }

                // Show options menu of the rutine
                public void showDialog(){

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.options_rutine);

                    TextView option_title = dialog.findViewById(R.id.rutine_options_title);
                    TextView option_play = dialog.findViewById(R.id.rutine_option_play);
                    TextView option_edit = dialog.findViewById(R.id.rutine_option_edit);
                    TextView option_duplicate = dialog.findViewById(R.id.rutine_option_duplicate);
                    TextView option_delete = dialog.findViewById(R.id.rutine_option_delete);

                    option_title.setText("Opciones > " + rutine.name);

                    // play click event
                    option_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        // TODO: Sent routine to play
                        public void onClick(View v) {
                            Log.d("demo","Play: "+ rutine.name);
                            PlayFragment playFragment = new PlayFragment(rutine);
                            FragmentTransaction fragmentTransaction = ((FragmentActivity) unwrap(v.getContext())).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment_content_start, playFragment);
                            fragmentTransaction.commit();
                            dialog.dismiss();
                        }
                    });

                    // edit click event
                    option_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        //Open Edit routine with selected routine
                        public void onClick(View v) {
                            Log.d("demo","Edit: "+ rutine.name);
                            EditRoutine newRutine = new EditRoutine(rutine);
                            ((FragmentActivity) unwrap(v.getContext())).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.nav_host_fragment_content_start, newRutine)
                                    .commit();
                            dialog.dismiss();
                        }
                    });
                    // duplicate click event
                    option_duplicate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        //TODO: Duplicate routine with a slightly different name
                        public void onClick(View v) {
                            Log.d("demo","Duplicate: "+ rutine.name);
                        }
                    });

                    // option_delete click event
                    option_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        //TODO: delete routine from database and reload routines

                        public void onClick(View v) {
                            Log.d("demo","Delete: "+ rutine.name);
                        }
                    });
                    dialog.show();
                }
            });
        }

        // for unwraping context in dialog
        private Context unwrap(Context context) {
            while (!(context instanceof Activity) && context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            }

            return context;
        }
    }
}