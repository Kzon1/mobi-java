package com.example.coursework_java.objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hikes")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public long hikeId;
    public String hikeName;
    public String location;
    public String hikeDate;
    public String statusParking;
    public int length;
    public String difficulty;
    public String description;

    public Hike(String hikeName, String location, String hikeDate, String statusParking, int length, String difficulty, String description) {
        this.hikeName = hikeName;
        this.location = location;
        this.hikeDate = hikeDate;
        this.statusParking = statusParking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
    }

    public long getHikeId() {
        return hikeId;
    }

    public void setHikeId(long hikeId) {
        this.hikeId = hikeId;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHikeDate() {
        return hikeDate;
    }

    public void setHikeDate(String hikeDate) {
        this.hikeDate = hikeDate;
    }

    public String getParking() {
        return statusParking;
    }

    public void setParking(String statusParking) {
        this.statusParking = statusParking;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
