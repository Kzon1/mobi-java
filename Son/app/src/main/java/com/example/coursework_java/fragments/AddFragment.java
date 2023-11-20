package com.example.coursework_java.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.coursework_java.R;
import com.example.coursework_java.adapters.HikeAdapter;
import com.example.coursework_java.database.HikeDatabase;
import com.example.coursework_java.objects.Hike;
import com.example.coursework_java.services.DatePickerFragment;

import java.util.Objects;

public class AddFragment extends Fragment {
    View view;
    Spinner difficultySpinner;
    EditText nameEditText,locationEditText,lengthEditText,descriptionEditText;
    static EditText dateEditText;
    Button addButton;
    RadioGroup parkingGroup;
    String[] difficultyLevels = {"Low", "Medium", "High", "Extreme High"};
    String parkingSelection = "default";
    HikeDatabase hikeDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.add_fragment, container, false);
         setUpMyView();
         handleLogic();

         hikeDatabase =  Room.databaseBuilder(this.getContext(), HikeDatabase.class, "myHikeDB")
                 .allowMainThreadQueries()
                 .build();

         dateEditText.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DialogFragment datePickerFragment = new DatePickerFragment();
                 datePickerFragment.show(getActivity().getSupportFragmentManager(), "Date Picker");
             }
         });
         addButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 handleValidInput();
             }
         });
        return view;
    }

    private void setUpMyView(){
        parkingGroup = view.findViewById(R.id.parking_radio_group);
        difficultySpinner = view.findViewById(R.id.difficultySpinner);
        nameEditText = view.findViewById(R.id.nameInput);
        locationEditText = view.findViewById(R.id.locationInput);
        dateEditText = view.findViewById(R.id.dateInput);
        lengthEditText = view.findViewById(R.id.lengthInput);
        descriptionEditText = view.findViewById(R.id.descriptionInput);
        addButton = view.findViewById(R.id.addButton);
    }

    private void handleLogic(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
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

    public static void selectDateByClick(int yyyy, int mm, int dd){
        dateEditText.setText(dd + "/" + mm + "/" + yyyy);
    }

    private void handleValidInput() {
        String name = nameEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String length = lengthEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String difficulty = difficultySpinner.getSelectedItem().toString();

        if (name.isEmpty() || location.isEmpty() || date.isEmpty()
                || length.isEmpty() || difficulty.isEmpty() || parkingSelection.equals("default")) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("All required fields must be filled")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        } else {
            addHike(name, location, date, parkingSelection, Integer.parseInt(length), difficulty, description);
        }
    }

    private void addHike(String name, String location, String date, String parkingSelection, int length, String difficulty, String description) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmation")
                .setMessage("New hike will be added: \n" +
                        "Name: " + name + "\n" +
                        "Location: " + location + "\n" +
                        "Date of the hike: " + date + "\n" +
                        "Parking available: " + parkingSelection + "\n" +
                        "Length of the hike: " + length + "\n" +
                        "Difficulty level: " + difficulty + "\n" +
                        "Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Hike hike = new Hike(name, location, date, parkingSelection, length, difficulty, description);
                        long hikeId = hikeDatabase.hikeDao().addHike(hike);
                        nameEditText.setText("");
                        locationEditText.setText("");
                        dateEditText.setText("");
                        parkingGroup.clearCheck();
                        lengthEditText.setText("");
                        difficultySpinner.setSelection(0);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
