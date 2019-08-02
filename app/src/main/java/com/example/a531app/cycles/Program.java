package com.example.a531app.cycles;

public class Program {

    private String programName;
    private static String[] programDays;
    private static String[] coreExercises;
    private static String[] secondaryExercises;

    public Program(String programName){
        this.programName = programName;
        programDays = new String[]{"Sunday", "Monday", "Wednesday", "Friday"};
        coreExercises = new String[]{"Overhead press", "Deadlift", "Bench press", "Squat"};
        secondaryExercises = new String[]{"Overhead press", "Deadlift", "Bench press", "Squat"};
    }

    public static String[] getProgramDays() {
        return programDays;
    }

    public void setProgramDays(String[] programDays) {
        this.programDays = programDays;
    }

    public static String[] getCoreExercises() {
        return coreExercises;
    }

    public void setCoreExercises(String[] coreExercises) {
        this.coreExercises = coreExercises;
    }

    public static String[] getSecondaryExercises() {
        return secondaryExercises;
    }

    public void setSecondaryExercises(String[] secondaryExercises) {
        this.secondaryExercises = secondaryExercises;
    }
}
