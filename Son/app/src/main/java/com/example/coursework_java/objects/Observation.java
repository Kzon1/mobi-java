package com.example.coursework_java.objects;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "observations", foreignKeys = {
        @ForeignKey(
                entity = Hike.class,
                parentColumns = {"hikeId"},
                childColumns = {"hikeId"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )})
public class Observation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long observationId;
    public String nameObservation;
    public String dateTime;
    public String comment;
    public long hikeId;

    public Observation(String nameObservation, String dateTime, String comment, long hikeId) {
        this.nameObservation = nameObservation;
        this.dateTime = dateTime;
        this.comment = comment;
        this.hikeId = hikeId;
    }

    public long getObservationId() {
        return observationId;
    }

    public void setObservationId(long observationId) {
        this.observationId = observationId;
    }

    public String getNameObservation() {
        return nameObservation;
    }

    public void setNameObservation(String nameObservation) {
        this.nameObservation = nameObservation;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getHikeId() {
        return hikeId;
    }

    public void setHikeId(long hikeId) {
        this.hikeId = hikeId;
    }
}
