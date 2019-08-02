package com.example.a531app.daysnavigation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a531app.R;
import com.example.a531app.utilities.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekCycleFragment extends BaseFragment {

    public static String WEEK_KEY = "com.example.a531app.week";

    private int week;
    private Context mContext;

    public WeekCycleFragment() {
        // Required empty public constructor
    }


    @Override
    public View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weekcycle, container, false);

        mContext= getActivity();

        week = getArguments().getInt("week");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), mContext, week);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = view.findViewById(R.id.days_tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public String actionTitle() {
        return "Week " + week;
    }

    @Override
    public boolean setBackButton() {
        return true;
    }

}
