package com.example.a531app.daysnavigation;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a531app.R;
import com.example.a531app.architecture.LiftListViewModel;
import com.example.a531app.architecture.LiftModel;
import com.example.a531app.utilities.Lift;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayThreeFragment extends Fragment {


    private RecyclerView recyclerView;
    private DayAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public DayThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        Bundle bundle = this.getArguments();
        int week = bundle.getInt("week");
        String secondary = bundle.getString("secondary");

        int id = bundle.getInt("id");
        LiftListViewModel model = ViewModelProviders.of(getActivity()).get(LiftListViewModel.class);

        recyclerView = view.findViewById(R.id.rv_day);
        recyclerView.setHasFixedSize(true);

        LiftModel lift = model.getLiftById(id);
        adapter = new DayAdapter(lift, week, secondary, getActivity(), model);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}
