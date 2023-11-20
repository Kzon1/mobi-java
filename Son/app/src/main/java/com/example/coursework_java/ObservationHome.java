package com.example.coursework_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.coursework_java.adapters.ObservationAdapter;
import com.example.coursework_java.database.HikeDatabase;

public class ObservationHome extends AppCompatActivity {
    private static final int ADD_STUDENT_REQUEST_CODE = 1;
    ObservationAdapter observationAdapter;
    RecyclerView recyclerView;
    Button newButton;
    HikeDatabase hikeDatabase;
    Toolbar toolbar;
    long hikeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_home);
        newButton = findViewById(R.id.newButton);
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hikeDatabase =  Room.databaseBuilder(ObservationHome.this, HikeDatabase.class, "myHikeDB")
                .allowMainThreadQueries()
                .build();

        Intent intent = getIntent();
        hikeId = intent.getLongExtra("hikeId", -1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ObservationHome.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        observationAdapter = new ObservationAdapter(ObservationHome.this,hikeDatabase,hikeId);
        recyclerView.setAdapter(observationAdapter);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ObservationHome.this, ObservationAdd.class);
                i.putExtra("myId", hikeId);
                startActivity(i);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        observationAdapter.refresh(hikeId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK) {
            observationAdapter.refresh(hikeId);
        }
    }
}