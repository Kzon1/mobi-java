package com.example.coursework_java.services;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface IDatePicker {
        void onDataSet(DatePicker datePicker, int y, int m, int d);
    }
    IDatePicker myListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            myListener = (IDatePicker) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " You have to implement IDatePicker");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        return new DatePickerDialog(getActivity(), this, year, --month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        myListener.onDataSet(datePicker, y,(m+1),d);
    }
}
