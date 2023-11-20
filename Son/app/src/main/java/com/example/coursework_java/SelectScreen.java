package com.example.coursework_java;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coursework_java.fragments.AddFragment;
import com.example.coursework_java.fragments.HomeFragment;
import com.example.coursework_java.fragments.SearchFragment;

public class SelectScreen  extends FragmentStateAdapter {
    public SelectScreen(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AddFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new SearchFragment();
            default:
                return new AddFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
