package com.example.homeworkoutapp.Recyclers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.objects.Filter;
import com.example.homeworkoutapp.ui.exercises.ExercisesFragment;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;

// Clase adaptador para la lista de filtros cuando buscamos ejercicios
public class FilterRecycler extends RecyclerView.Adapter<FilterRecycler.itemFilter>{

    ArrayList<Filter> list_filters;
    private ExercisesFragment currentFragment;

    //Constructor de la clase
    public FilterRecycler(ArrayList<Filter> listRutines, ExercisesFragment exercisesFragment) {
        this.currentFragment = exercisesFragment;
        this.list_filters = listRutines;
    }

    //Inicializador de los items de la lista
    @NonNull
    @Override
    public itemFilter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Aqui se le coloca el layout que va a utilizar el item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_exercises,null,false);
        itemFilter rutine = new itemFilter(view);
        return rutine;
    }

    //Carga con los valores a los item de la lista
    @Override
    public void onBindViewHolder(@NonNull itemFilter holder, int position) {
        Filter object = list_filters.get(position);
        holder.image.setImageDrawable(getMyDrawable(holder.context,object.image_location));
        holder.filter = object;
    }

    //Obtiene el numero de items que debe de generar
    @Override
    public int getItemCount() {
        return list_filters.size();
    }

    // Clase del item que se va a generar en al lista de filtros de la rutina
    public class itemFilter extends RecyclerView.ViewHolder {
        private Context context;

        private long selected;

        Filter filter;

        // Objects in item rutine
        ImageView image;
        MaterialCardView cotainer;

        //Contructor del item
        public itemFilter(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // Getting id's of objects in item
            image = itemView.findViewById(R.id.filter_image);
            cotainer = itemView.findViewById(R.id.itemFilter);

            // Listen click on all the item
            cotainer.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    selected = getItemId();
                    currentFragment.setFilter(filter.filter_id, filter.description);
                    Log.d("demo","Click on filter.");
                }
            });
        }
    }

    // para cargar la imagen del filtro
    Drawable getMyDrawable(Context c,String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }
}
