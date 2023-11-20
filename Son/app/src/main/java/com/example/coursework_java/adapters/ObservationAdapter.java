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

import com.example.coursework_java.ObservationAdd;
import com.example.coursework_java.ObservationHome;
import com.example.coursework_java.ObservationUpdate;
import com.example.coursework_java.R;
import com.example.coursework_java.database.HikeDatabase;
import com.example.coursework_java.objects.Hike;
import com.example.coursework_java.objects.Observation;
import com.google.gson.Gson;

import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder>{
    Context context;
    HikeDatabase hikeDatabase;
    List<Observation> list;
    long hikeId;

    public ObservationAdapter(Context context, HikeDatabase hikeDatabase, long hikeId){
        this.context = context;
        this.hikeDatabase = hikeDatabase;
        this.hikeId = hikeId;
        resetData();
    }
    public void resetData(){
        list = hikeDatabase.observationDAO().getObservationsByHike(hikeId);
        notifyDataSetChanged();
    }
    public void refresh(long id){
        list = hikeDatabase.observationDAO().getObservationsByHike(id);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike,parent,false);
        return new ObservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        Observation myObservation = list.get(position);
        holder.observationHike.setText(myObservation.getNameObservation());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmation")
                        .setMessage("Do you want to delete all hike?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hikeDatabase.observationDAO().deleteObservation(myObservation);
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
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ObservationUpdate.class);
                Gson gson = new Gson();
                String hikeJson = gson.toJson(myObservation);
                i.putExtra("observationJson", hikeJson);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list.size() != 0){
            return list.size();
        }
        return 0;
    }

    public class ObservationViewHolder extends RecyclerView.ViewHolder{
        EditText observationHike;
        Button deleteButton, updateButton;


        public ObservationViewHolder(@NonNull View itemView) {
            super(itemView);
            observationHike = itemView.findViewById(R.id.hikeName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            updateButton = itemView.findViewById(R.id.moreButton);
            updateButton.setText("Edit");
        }
    }
}
