package com.example.a531app.cycles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a531app.R;

public class Weeks extends AppCompatActivity implements WeeksAdapter.WeeksAdapterClickListener {


    private RecyclerView recyclerView;
    private WeeksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weeks);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Weeks");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        recyclerView = findViewById(R.id.rv_weeks);
        recyclerView.setHasFixedSize(true);

        adapter = new WeeksAdapter(this, this);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    @Override
    public void onClick(int week) {
        Intent intent = new Intent(this, WeekCycle.class);
        intent.putExtra(WeekCycle.WEEK_KEY, week);
        startActivity(intent);
    }
}
