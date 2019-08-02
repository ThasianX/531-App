package com.example.a531app.navigation;


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
import com.example.a531app.cycles.DayAdapter;
import com.example.a531app.lifts.Lift;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayTwoFragment extends Fragment {

    private RecyclerView recyclerView;
    private DayAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public DayTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        Bundle bundle = this.getArguments();
        Lift lift = bundle.getParcelable("lift");
        int week = bundle.getInt("week");
        String secondary = bundle.getString("secondary");


        recyclerView = view.findViewById(R.id.rv_day);
        recyclerView.setHasFixedSize(true);

        adapter = new DayAdapter(lift, week, secondary, getActivity());
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

}
