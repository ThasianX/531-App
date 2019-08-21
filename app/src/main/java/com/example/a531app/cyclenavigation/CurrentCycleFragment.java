package com.example.a531app.cyclenavigation;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a531app.R;
import com.example.a531app.utilities.BaseFragment;
import com.example.a531app.daysnavigation.WeekCycleFragment;
import com.example.a531app.utilities.Lift;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentCycleFragment extends BaseFragment implements WeeksAdapter.WeeksAdapterClickListener {


    private RecyclerView recyclerView;
    private WeeksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static final String CYCLE_DATE_KEY = "com.example.a531app.cycledate";
    private TextView startDate;
    private Button completeCycle;


    public CurrentCycleFragment() {
        // Required empty public constructor

    }


    @Override
    public View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_cycle, container, false);

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rv_weeks);
        recyclerView.setHasFixedSize(true);

        adapter = new WeeksAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        startDate = view.findViewById(R.id.tv_start_date);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(CycleManager.SP_NAME, Context.MODE_PRIVATE);
        startDate.setText("Cycle began on " + sharedPreferences.getString(CYCLE_DATE_KEY, ""));

        completeCycle = view.findViewById(R.id.btn_complete_cycle);
        completeCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Lift> lifts = CycleManager.getLifts();
                for(Lift lift : lifts){
                    lift.setTraining_max(lift.getTraining_max()+lift.getProgression());
                }
                //Add more later
            }
        });

        return view;
    }



    @Override
    public String actionTitle() {
        return "Manage Current Cycle";
    }

    @Override
    public boolean setBackButton() {
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.managermenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_reset:
                reset();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int week) {
//        Intent intent = new Intent(getActivity(), WeekCycle.class);
//        intent.putExtra(WeekCycle.WEEK_KEY, week);
//        startActivity(intent);

        WeekCycleFragment fragment = new WeekCycleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("week", week);
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void reset(){
        String date = getDate();
        startDate.setText("Cycle began on " + date);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(CycleManager.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CYCLE_DATE_KEY, date);
        editor.apply();




    }

    private String getDate(){
        Calendar rightNow = Calendar.getInstance();
        String month = rightNow.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day = rightNow.get(rightNow.DAY_OF_MONTH);
        int year = rightNow.get(rightNow.YEAR);
        return month + " " + day + ", " + year;
    }

}
