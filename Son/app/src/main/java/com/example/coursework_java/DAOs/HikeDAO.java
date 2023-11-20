package com.example.coursework_java.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursework_java.objects.Hike;

import java.util.List;

@Dao
public interface HikeDAO {
    @Insert
    long addHike(Hike hike);

    @Update
    void updateHike(Hike hike);

    @Delete
    void deleteHike(Hike hike);

    @Query("DELETE FROM hikes")
    void deleteAllHike();

    @Query("SELECT * From hikes WHERE hikeName like '%' || :name || '%' ")
    List<Hike> searchByName(String name);

    @Query("SELECT * FROM hikes ORDER BY hikeName")
    List<Hike> getAllHikes();
}

