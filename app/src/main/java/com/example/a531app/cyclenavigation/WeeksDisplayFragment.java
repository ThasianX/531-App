package com.example.a531app.cyclenavigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a531app.R;
import com.example.a531app.appnavigation.BaseFragment;
import com.example.a531app.cycles.WeekCycle;
import com.example.a531app.cycles.WeeksAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeksDisplayFragment extends BaseFragment implements WeeksAdapter.WeeksAdapterClickListener {

    private RecyclerView recyclerView;
    private WeeksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public WeeksDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weeks, container, false);

        recyclerView = view.findViewById(R.id.rv_weeks);
        recyclerView.setHasFixedSize(true);

        adapter = new WeeksAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public String actionTitle() {
        return "Weeks";
    }

    @Override
    public boolean setBackButton() {
        return true;
    }

    @Override
    public void onClick(int week) {
        Intent intent = new Intent(getActivity(), WeekCycle.class);
        intent.putExtra(WeekCycle.WEEK_KEY, week);
        startActivity(intent);
    }
}
