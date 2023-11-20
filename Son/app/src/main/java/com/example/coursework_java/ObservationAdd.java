package com.example.coursework_java;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursework_java.database.HikeDatabase;
import com.example.coursework_java.objects.Observation;

public class ObservationAdd extends AppCompatActivity {
    EditText observationEditText, timeEditText, commentEditText;
    Button addButton;
    HikeDatabase hikeDatabase;
    Toolbar toolbar;
    long hikeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_add);
        hikeDatabase =  Room.databaseBuilder(ObservationAdd.this, HikeDatabase.class, "myHikeDB")
                .allowMainThreadQueries()
                .build();
        setUpMyView();
        Intent intent = getIntent();
         hikeId = intent.getLongExtra("myId", -1);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleValidInput();
            }
        });

    }

    private void handleValidInput() {
        String observation = observationEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String comment = commentEditText.getText().toString();

        if (observation.isEmpty() || time.isEmpty()) {
            new AlertDialog.Builder(ObservationAdd.this)
                    .setTitle("Error")
                    .setMessage("All required fields must be filled")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        } else {
            addObservation(observation, time, comment);
        }
    }

    private void addObservation(String observation, String time, String comment){
        Observation myObservation = new Observation(observation,time,comment,hikeId);
        Log.e("error","errorrrrrr here");
        hikeDatabase.observationDAO().addObservation(myObservation);
        observationEditText.setText("");
        timeEditText.setText("");
        commentEditText.setText("");
        Toast.makeText(ObservationAdd.this, "Add successfully", Toast.LENGTH_SHORT).show();
    }

    private void setUpMyView(){
        observationEditText = findViewById(R.id.observationInput);
        timeEditText = findViewById(R.id.timeInput);
        commentEditText = findViewById(R.id.commentInput);
        addButton = findViewById(R.id.addButton);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}