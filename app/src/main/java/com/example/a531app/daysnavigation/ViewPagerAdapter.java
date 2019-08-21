package com.example.a531app.daysnavigation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.a531app.architecture.LiftListViewModel;
import com.example.a531app.architecture.LiftModel;
import com.example.a531app.cyclenavigation.CycleManager;
import com.example.a531app.utilities.Program;
import com.example.a531app.utilities.Lift;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private Context mContext;
    private List<LiftModel> mLifts;
    private int week;
    private Program program;
    private String[] programDays;
    private String[] coreExercises;
    private String[] secondaryExercises;


    public ViewPagerAdapter(FragmentManager fm, Context context, int week, List<LiftModel> lifts) {
        super(fm);
        mContext = context;
        this.week = week;
        mLifts = lifts;
        program = CycleManager.getProgram();
        programDays = program.getProgramDays();
        coreExercises = program.getCoreExercises();
        secondaryExercises = program.getSecondaryExercises();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            String exercise = coreExercises[0];
            for(LiftModel lift: mLifts){
                if(lift.getName().equals(exercise)){
                    DayOneFragment fragment = new DayOneFragment();
                    fragment.setArguments(returnBundle(lift.exercise_id, secondaryExercises[0]));
                    return fragment;
                }
            }
        } else if (position == 1) {
            String exercise = coreExercises[1];
            for(LiftModel lift: mLifts){
                if(lift.getName().equals(exercise)){
                    DayTwoFragment fragment = new DayTwoFragment();
                    fragment.setArguments(returnBundle(lift.exercise_id, secondaryExercises[1]));
                    return fragment;
                }
            }
        } else if (position == 2) {
            String exercise = coreExercises[2];
            for(LiftModel lift: mLifts){
                if(lift.getName().equals(exercise)){
                    DayThreeFragment fragment = new DayThreeFragment();
                    fragment.setArguments(returnBundle(lift.exercise_id, secondaryExercises[2]));
                    return fragment;
                }
            }
        } else {
            String exercise = coreExercises[3];
            for (LiftModel lift : mLifts) {
                if (lift.getName().equals(exercise)) {
                    DayFourFragment fragment = new DayFourFragment();
                    fragment.setArguments(returnBundle(lift.exercise_id, secondaryExercises[3]));
                    return fragment;
                }
            }
        }
        return null;
    }

    public Bundle returnBundle(int id, String secondary){
        Bundle args = new Bundle();
        args.putInt("week", week);
        args.putInt("id", id);
        args.putString("secondary", secondary);
        return args;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return programDays[0];
            case 1:
                return programDays[1];
            case 2:
                return programDays[2];
            case 3:
                return programDays[3];
            default:
                return null;

        }
    }
}
