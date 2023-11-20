package com.example.coursework_java.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.coursework_java.R;
import com.example.coursework_java.adapters.HikeAdapter;
import com.example.coursework_java.database.HikeDatabase;


public class HomeFragment extends Fragment {
    View view;
    HikeAdapter hikeAdapter;
    HikeDatabase hikeDatabase;
    RecyclerView recyclerView;
    Button deleteAllButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        hikeDatabase =  Room.databaseBuilder(this.getContext(), HikeDatabase.class, "myHikeDB")
                .allowMainThreadQueries()
                .build();
        recyclerView = view.findViewById(R.id.recyclerView);
        deleteAllButton = view.findViewById(R.id.deleteAllButton);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        hikeAdapter = new HikeAdapter(this.getContext(), hikeDatabase);
        recyclerView.setAdapter(hikeAdapter);
        hikeAdapter.notifyDataSetChanged();

        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Do you want to delete all hike?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hikeDatabase.hikeDao().deleteAllHike();
                                hikeAdapter.resetData();
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
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        hikeAdapter.resetData();
    }
}
