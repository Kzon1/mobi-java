package com.example.coursework_java;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coursework_java.database.HikeDatabase;
import com.example.coursework_java.objects.Hike;
import com.example.coursework_java.services.DatePickerFragment;
import com.google.gson.Gson;

public class EditHike extends AppCompatActivity implements DatePickerFragment.IDatePicker{
    Spinner difficultySpinner;
    EditText nameEditText,locationEditText,lengthEditText,descriptionEditText;
    static EditText dateEditText;
    Button updateButton;
    RadioGroup parkingGroup;
    RadioButton yesButton, noButton;
    Toolbar toolbar;
    String[] difficultyLevels = {"Low", "Medium", "High", "Extreme High"};
    String parkingSelection = "default";
    HikeDatabase hikeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);

        String hikeJson = getIntent().getStringExtra("hikeJson");
        Gson gson = new Gson();
        Hike myHike = gson.fromJson(hikeJson, Hike.class);

        hikeDatabase =  Room.databaseBuilder(EditHike.this, HikeDatabase.class, "myHikeDB")
                .allowMainThreadQueries()
                .build();

        setUpMyView();
        handleLogic();
        setData(myHike);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(EditHike.this.getSupportFragmentManager(), "Date Picker");
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleValidInput(myHike);
            }
        });
    }

    private void setData(Hike hike){
        nameEditText.setText(hike.getHikeName());
        dateEditText.setText(hike.getHikeDate());
        locationEditText.setText(hike.getLocation());
        descriptionEditText.setText(hike.getDescription());
        lengthEditText.setText(String.valueOf(hike.getLength()));
        if(hike.getParking().equals("Yes")){
            yesButton.setChecked(true);
            parkingSelection = "Yes";
        }else{
            noButton.setChecked(true);
            parkingSelection = "No";
        }

    }

    private void setUpMyView(){
        parkingGroup = findViewById(R.id.parking_radio_group);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        nameEditText = findViewById(R.id.nameInput);
        locationEditText = findViewById(R.id.locationInput);
        dateEditText = findViewById(R.id.dateInput);
        lengthEditText = findViewById(R.id.lengthInput);
        descriptionEditText = findViewById(R.id.descriptionInput);
        updateButton = findViewById(R.id.updateButton);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void handleLogic(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditHike.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, difficultyLevels);
        difficultySpinner.setAdapter(adapter);
        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String myItem = (String) adapterView.getItemAtPosition(i);
                if (!myItem.isEmpty()) {
                    difficultySpinner.setSelection(i);
                    difficultySpinner.setVisibility(View.VISIBLE);
                } else {
                    difficultySpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                difficultySpinner.setVisibility(View.VISIBLE);
            }
        });

        parkingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.yesButton){
                    parkingSelection = "Yes";
                } else if (i == R.id.noButton) {
                    parkingSelection = "No";
                }
            }
        });
    }

    private void handleValidInput(Hike hike) {
        String name = nameEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String length = lengthEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String difficulty = difficultySpinner.getSelectedItem().toString();

        if (name.isEmpty() || location.isEmpty() || date.isEmpty()
                || length.isEmpty() || difficulty.isEmpty() || parkingSelection.equals("default")) {
            new AlertDialog.Builder(EditHike.this)
                    .setTitle("Error")
                    .setMessage("All required fields must be filled")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        } else {
            updateHike(name, location, date, parkingSelection, Integer.parseInt(length), difficulty, description,hike);
        }
    }

    private void updateHike(String name, String location, String date, String parkingSelection, int length, String difficulty, String description, Hike hike) {
        new AlertDialog.Builder(EditHike.this)
                .setTitle("Confirmation")
                .setMessage("Updated hike will be changed: \n" +
                        "Name: " + name + "\n" +
                        "Location: " + location + "\n" +
                        "Date of the hike: " + date + "\n" +
                        "Parking available: " + parkingSelection + "\n" +
                        "Length of the hike: " + length + "\n" +
                        "Difficulty level: " + difficulty + "\n" +
                        "Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hike.setHikeName(name);
                        hike.setHikeDate(date);
                        hike.setLocation(location);
                        hike.setLength(length);
                        hike.setParking(parkingSelection);
                        hike.setDescription(description);
                        hike.setDifficulty(difficulty);
                        hikeDatabase.hikeDao().updateHike(hike);
                        Toast.makeText(EditHike.this, "Update successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    public void onDataSet(DatePicker datePicker, int yyyy, int mm, int dd) {
        dateEditText.setText(dd + "/" + mm + "/" + yyyy);
    }
}