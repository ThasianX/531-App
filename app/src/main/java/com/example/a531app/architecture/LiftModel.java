package com.example.a531app.architecture;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class LiftModel {

    @PrimaryKey(autoGenerate = true)
    public int exercise_id;

    private String name;
    private double progression;
    private double training_max;
    private double round_to;
    private int personal_record;
    private int day;
    private String assistance;

    public LiftModel(String name, double progression, double training_max, double round_to, int personal_record, int day, String assistance) {
        this.name = name;
        this.progression = progression;
        this.training_max = training_max;
        this.round_to = round_to;
        this.personal_record = personal_record;
        this.day = day;
        this.assistance = assistance;
    }

    public String getName() {
        return name;
    }

    public double getProgression() {
        return progression;
    }

    public void setProgression(double progression) {
        this.progression = progression;
    }

    public double getTraining_max() {
        return training_max;
    }

    public void setTraining_max(double training_max) {
        this.training_max = training_max;
    }

    public double getRound_to() {
        return round_to;
    }

    public void setRound_to(double round_to) {
        this.round_to = round_to;
    }

    public int getPersonal_record() {
        return personal_record;
    }

    public void setPersonal_record(int personal_record) {
        this.personal_record = personal_record;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getAssistance() {
        return assistance;
    }

    public void setAssistance(String assistance) {
        this.assistance = assistance;
    }
}
