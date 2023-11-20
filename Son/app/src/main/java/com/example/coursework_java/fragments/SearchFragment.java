package com.example.coursework_java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.coursework_java.R;
import com.example.coursework_java.adapters.SearchingAdapter;
import com.example.coursework_java.database.HikeDatabase;
import com.example.coursework_java.objects.Hike;

import java.util.List;

public class SearchFragment extends Fragment {
    View view;
    EditText searchEditText;
    Button searchButton;
    RecyclerView recyclerView;
    SearchingAdapter searchingAdapter;
    HikeDatabase hikeDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        searchEditText = view.findViewById(R.id.searchInput);
        searchButton = view.findViewById(R.id.searchButton);
        recyclerView = view.findViewById(R.id.recyclerView);

        hikeDatabase =  Room.databaseBuilder(this.getContext(), HikeDatabase.class, "myHikeDB")
                .allowMainThreadQueries()
                .build();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        searchingAdapter = new SearchingAdapter(requireContext());
        recyclerView.setAdapter(searchingAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = searchEditText.getText().toString();
                List<Hike> listHike = hikeDatabase.hikeDao().searchByName(input);
                searchingAdapter.findData(listHike);
            }
        });
        return view;
    }
}
