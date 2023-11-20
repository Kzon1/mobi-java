package com.example.coursework_java.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework_java.EditHike;
import com.example.coursework_java.ObservationHome;
import com.example.coursework_java.R;
import com.example.coursework_java.database.HikeDatabase;
import com.example.coursework_java.objects.Hike;
import com.example.coursework_java.objects.Observation;
import com.google.gson.Gson;

import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder>{
    Context context;
    HikeDatabase hikeDatabase;
    List<Hike> list;

    public HikeAdapter(Context context, HikeDatabase hikeDatabase){
        this.context = context;
        this.hikeDatabase = hikeDatabase;
        resetData();
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike,parent,false);
        return new HikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        Hike myHike = list.get(position);
        holder.nameHike.setText(myHike.getHikeName());
        holder.nameHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditHike.class);
                Gson gson = new Gson();
                String hikeJson = gson.toJson(myHike);
                intent.putExtra("hikeJson", hikeJson);
                context.startActivity(intent);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmation")
                        .setMessage("Do you want to delete all hike?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hikeDatabase.hikeDao().deleteHike(myHike);
                                resetData();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });

        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ObservationHome.class);
                intent.putExtra("hikeId",myHike.getHikeId());
                context.startActivity(intent);
            }
        });

    }

    public void resetData(){
        list = hikeDatabase.hikeDao().getAllHikes();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(list.size() != 0){
            return list.size();
        }
        return 0;
    }

    public class HikeViewHolder extends RecyclerView.ViewHolder{
        EditText nameHike;
        Button deleteButton, moreButton;


        public HikeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameHike = itemView.findViewById(R.id.hikeName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            moreButton = itemView.findViewById(R.id.moreButton);
        }
    }
}
