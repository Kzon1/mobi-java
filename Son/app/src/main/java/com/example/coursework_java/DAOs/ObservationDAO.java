package com.example.coursework_java.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursework_java.objects.Hike;
import com.example.coursework_java.objects.Observation;

import java.util.List;

@Dao
public interface ObservationDAO {
    @Insert
    long addObservation(Observation observation);

    @Update
    void updateObservation(Observation observation);

    @Delete
    void deleteObservation(Observation observation);

    @Query("SELECT * FROM observations WHERE observationId =:observationId")
    Observation findObservation(long observationId);

    @Query("SELECT * FROM observations ORDER BY observationId")
    List<Observation> getAllObservations();

    @Query("SELECT * FROM observations WHERE hikeId =:hikeId ORDER BY observationId")
    List<Observation> getObservationsByHike(long hikeId);
}
