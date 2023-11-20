package com.example.coursework_java;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursework_java.database.HikeDatabase;
import com.example.coursework_java.objects.Hike;
import com.example.coursework_java.objects.Observation;
import com.google.gson.Gson;

public class ObservationUpdate extends AppCompatActivity {
    EditText observationEditText, timeEditText, commentEditText;
    Button updateButton;
    Toolbar toolbar;
    HikeDatabase hikeDatabase;
    Observation observationObject;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_update);

        String observationJson = getIntent().getStringExtra("observationJson");
        Gson gson = new Gson();
        observationObject = gson.fromJson(observationJson, Observation.class);
        id=observationObject.getObservationId();

        hikeDatabase =  Room.databaseBuilder(ObservationUpdate.this, HikeDatabase.class, "myHikeDB")
                .allowMainThreadQueries()
                .build();

        setUpMyView();
        setData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleValidInput();
            }
        });
    }

    private void setData(){
        observationEditText.setText(observationObject.getNameObservation());
        timeEditText.setText(observationObject.getDateTime());
        commentEditText.setText(observationObject.getComment());
    }

    private void setUpMyView(){
        observationEditText = findViewById(R.id.observationInput);
        timeEditText = findViewById(R.id.timeInput);
        commentEditText = findViewById(R.id.commentInput);
        updateButton = findViewById(R.id.updateButton);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void handleValidInput() {
        String observationName = observationEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String comment = commentEditText.getText().toString();

        if (observationName.isEmpty() || time.isEmpty()) {
            new AlertDialog.Builder(ObservationUpdate.this)
                    .setTitle("Error")
                    .setMessage("All required fields must be filled")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        } else {
            updateObservation(observationName, time, comment);
        }
    }

    private void updateObservation(String observationName, String time, String comment){
        observationObject.setNameObservation(observationName);
        observationObject.setDateTime(time);
        observationObject.setComment(comment);
        hikeDatabase.observationDAO().updateObservation(observationObject);
        Toast.makeText(ObservationUpdate.this, "Update successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ObservationUpdate.this, ObservationHome.class);
        intent.putExtra("hikeId",observationObject.getHikeId());
        startActivity(intent);
    }
}