package com.example.coursework_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.example.coursework_java.fragments.AddFragment;
import com.example.coursework_java.services.DatePickerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements DatePickerFragment.IDatePicker{
    BottomNavigationView bottomMenu;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomMenu = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SelectScreen(this));
        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.addScreen) {
                    viewPager.setCurrentItem(0);
                } else if (item.getItemId() == R.id.homeScreen) {
                    viewPager.setCurrentItem(1);
                } else if (item.getItemId() == R.id.searchScreen) {
                    viewPager.setCurrentItem(2);
                }
                return true;
            }
        });
    }

    @Override
    public void onDataSet(DatePicker datePicker, int yyyy, int mm, int dd) {
        AddFragment.selectDateByClick(yyyy,mm,dd);
    }
}