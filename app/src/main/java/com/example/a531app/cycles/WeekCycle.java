package com.example.a531app.cycles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.a531app.R;
import com.example.a531app.lifts.Lift;
import com.example.a531app.navigation.ViewPagerAdapter;

import java.util.List;

public class WeekCycle extends AppCompatActivity{

    public static String WEEK_KEY = "com.example.a531app.week";

    private int week;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekcycle);

        //For onclick listener
        mContext= this;


        //set title to passed in intent number - convert to week
        Intent intent = getIntent();
        week = intent.getIntExtra(WEEK_KEY, -1);
        getSupportActionBar().setTitle("Week " + week);


        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mContext, week);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = findViewById(R.id.days_tab);
        tabLayout.setupWithViewPager(viewPager);



    }


}
