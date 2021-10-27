package com.example.homeworkoutapp.Recyclers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkoutapp.R;

import java.io.File;
import java.util.ArrayList;

public class FilterRecycler extends RecyclerView.Adapter<FilterRecycler.Filter>{

    ArrayList<String> list_filters;

    public FilterRecycler(ArrayList<String> listRutines) {
        this.list_filters = listRutines;
    }

    @NonNull
    @Override
    public Filter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_exercises,null,false);
        FilterRecycler.Filter rutine = new FilterRecycler.Filter(view);
        return rutine;
    }

    @Override
    public void onBindViewHolder(@NonNull Filter holder, int position) {
        String filter_type = list_filters.get(position);
        String image_path = "";

        File imgFile = new File(image_path);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.image.setImageBitmap(myBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return list_filters.size();
    }

    public class Filter extends RecyclerView.ViewHolder {
        private Context context;

        // Objects in item rutine
        ImageView image;


        public Filter(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // Getting id's of objects in item
            image = itemView.findViewById(R.id.filter_image);

            // Listen click on all the item
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("demo","Click on filter.");
                }
            });
        }
    }
}
