package com.example.coursework_java.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.coursework_java.DAOs.HikeDAO;
import com.example.coursework_java.DAOs.ObservationDAO;
import com.example.coursework_java.objects.Hike;
import com.example.coursework_java.objects.Observation;

@Database(entities = {Hike.class, Observation.class}, version = 1)
public abstract class HikeDatabase extends RoomDatabase {
    public abstract HikeDAO hikeDao();
    public abstract ObservationDAO observationDAO();
}
