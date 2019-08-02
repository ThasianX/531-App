package com.example.a531app.cycles;

public class WeekPercentages {

    private static int[] coresetPercentages1;
    private static int[] coresetPercentages2;
    private static int[] secondarysetPercentages;
    private static int[] coresetReps;

    public static int[] getCoresetReps() {
        return coresetReps;
    }

    public static void setCoresetReps(int[] coresetReps) {
        WeekPercentages.coresetReps = coresetReps;
    }

    public WeekPercentages(){
        coresetPercentages1 = new int[]{65, 75, 85, 70, 80, 90, 75, 85, 95, 40, 50, 60};
        coresetPercentages2 = new int[]{75, 80, 85, 80, 85, 90, 75, 85, 95, 40, 50, 60};
        secondarysetPercentages = new int[]{60, 60, 60, 60, 60};
        coresetReps = new int[]{5,5,5,3,3,3,5,3,1,5,5,5};
    }

    public static int[] getCoresetPercentages1() {
        return coresetPercentages1;
    }

    public void setCoresetPercentages(int[] coresetPercentages) {
        this.coresetPercentages1 = coresetPercentages;
    }

    public int[] getSecondarysetPercentages() {
        return secondarysetPercentages;
    }

    public void setSecondarysetPercentages(int[] secondarysetPercentages) {
        this.secondarysetPercentages = secondarysetPercentages;
    }
}
