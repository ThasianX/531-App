package com.example.a531app.lifts;

import android.os.Parcel;
import android.os.Parcelable;

public class Lift implements Parcelable {

    private final String name;
    private int exercise_id;
    private double progression;
    private double training_max;
    private double round_to;
    private int personal_record;
    private int day;


    public Lift(String name, int exercise_id, double progression, double training_max, double round_to, int personal_record, int day) {
        this.name = name;
        this.exercise_id = exercise_id;
        this.progression = progression;
        this.training_max = training_max;
        this.round_to = round_to;
        this.personal_record = personal_record;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(exercise_id);
        dest.writeDouble(progression);
        dest.writeDouble(training_max);
        dest.writeDouble(round_to);
        dest.writeInt(personal_record);
        dest.writeInt(day);
    }

    public Lift(Parcel parcel){
        name = parcel.readString();
        exercise_id = parcel.readInt();
        progression = parcel.readDouble();
        training_max = parcel.readDouble();
        round_to = parcel.readDouble();
        personal_record = parcel.readInt();
        day = parcel.readInt();
    }

    public static final Parcelable.Creator<Lift> CREATOR = new Parcelable.Creator<Lift>(){

        @Override
        public Lift createFromParcel(Parcel source) {
            return new Lift(source);
        }

        @Override
        public Lift[] newArray(int size) {
            return new Lift[size];
        }
    };




}
