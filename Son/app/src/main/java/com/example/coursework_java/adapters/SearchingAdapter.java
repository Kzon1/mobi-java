package com.example.coursework_java.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework_java.R;
import com.example.coursework_java.objects.Hike;

import java.util.ArrayList;
import java.util.List;

public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.HikeSearchViewHolder>{
    Context context;
    List<Hike> list;

    public SearchingAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public HikeSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_search,parent,false);
        return new HikeSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeSearchViewHolder holder, int position) {
        Hike myHike = list.get(position);
        holder.nameHike.setText(myHike.getHikeName());
    }

    @Override
    public int getItemCount() {
        if(list.size() != 0){
            return list.size();
        }
        return 0;
    }
    public void findData(List<Hike> passedList){
        list = passedList;
        notifyDataSetChanged();
        if(list.size()==0){
            Toast.makeText(context, "No matching hike", Toast.LENGTH_SHORT).show();
        }
    }

    public class HikeSearchViewHolder extends RecyclerView.ViewHolder{
        EditText nameHike;

        public HikeSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            nameHike = itemView.findViewById(R.id.hikeName);
        }
    }
}

