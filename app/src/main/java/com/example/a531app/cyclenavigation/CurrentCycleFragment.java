package com.example.a531app.cyclenavigation;


import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.a531app.R;
import com.example.a531app.architecture.LiftListViewModel;
import com.example.a531app.settingsnavigation.SettingsFragment;
import com.example.a531app.utilities.BaseFragment;
import com.example.a531app.daysnavigation.WeekCycleFragment;

import java.util.Calendar;
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
    private Button startCycle;

    public static final String WEEK_1_KEY = "progress_week_1";
    public static final String WEEK_2_KEY = "progress_week_2";
    public static final String WEEK_3_KEY = "progress_week_3";
    public static final String WEEK_4_KEY = "progress_week_4";

    public static final String CYCLE_STARTED_KEY = "started";


    public CurrentCycleFragment() {
        // Required empty public constructor

    }


    @Override
    public View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_cycle, container, false);

        setHasOptionsMenu(true);

        final LiftListViewModel model = ViewModelProviders.of(getActivity()).get(LiftListViewModel.class);

        recyclerView = view.findViewById(R.id.rv_weeks);
        recyclerView.setHasFixedSize(true);

        adapter = new WeeksAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        startDate = view.findViewById(R.id.tv_start_date);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(CycleManager.SP_NAME, Context.MODE_PRIVATE);

        startCycle = view.findViewById(R.id.btn_start_cycle);
        startCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCycle.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(CYCLE_DATE_KEY, getDate());
                editor.putBoolean(CYCLE_STARTED_KEY, true);
                startDate.setText("Cycle began on " + getDate());
                startDate.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                completeCycle.setVisibility(View.VISIBLE);
                editor.apply();
            }
        });
        completeCycle = view.findViewById(R.id.btn_complete_cycle);
        completeCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Need to delete all checkboxes but reinput the ones I need to keep. So prob get the values and keys I need before reset.
                complete();

                model.increaseLifts();
                model.resetPrs();


                //Add more later
            }
        });

        boolean started = sharedPreferences.getBoolean(CYCLE_STARTED_KEY, false);
        if(started){
            startCycle.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            completeCycle.setVisibility(View.VISIBLE);
            startDate.setText("Cycle began on " + sharedPreferences.getString(CYCLE_DATE_KEY,""));
            startDate.setVisibility(View.VISIBLE);
        } else {
            startCycle.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            completeCycle.setVisibility(View.GONE);
            startDate.setVisibility(View.GONE);
        }

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
        bundle.putString("date", getDate());
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void complete(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.complete_title).setMessage(R.string.complete_message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetSp();

                startCycle.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                completeCycle.setVisibility(View.GONE);
                startDate.setVisibility(View.GONE);


                Toast.makeText(getActivity(), "Cycle completed. All training maxes increased by progression amount. PRs reset for new cycle.", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }


    private void resetSp(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(CycleManager.SP_NAME, Context.MODE_PRIVATE);

        boolean coreTimerEnabled = sharedPreferences.getBoolean(SettingsFragment.CORE_CHECKED_KEY, true);
        boolean secondaryTimerEnabled =sharedPreferences.getBoolean(SettingsFragment.SECONDARY_CHECKED_KEY, true);
        boolean assistanceTimerEnabled = sharedPreferences.getBoolean(SettingsFragment.ASSISTANCE_CHECKED_KEY, true);

        boolean firstLaunch = sharedPreferences.getBoolean(CycleManager.FIRST_LAUNCH_KEY, false);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        //Clear SP before putting more
        editor.putBoolean(SettingsFragment.CORE_CHECKED_KEY, coreTimerEnabled);
        editor.putBoolean(SettingsFragment.SECONDARY_CHECKED_KEY, secondaryTimerEnabled);
        editor.putBoolean(SettingsFragment.ASSISTANCE_CHECKED_KEY, assistanceTimerEnabled);
        editor.putBoolean(CycleManager.FIRST_LAUNCH_KEY, firstLaunch);
        editor.apply();

        adapter.notifyDataSetChanged();

    }

    private void reset(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.reset_title).setMessage(R.string.reset_message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetSp();

                startCycle.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                completeCycle.setVisibility(View.GONE);
                startDate.setVisibility(View.GONE);


                Toast.makeText(getActivity(), "Cycle reset. PRs reset for new cycle.", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    private String getDate(){
        Calendar rightNow = Calendar.getInstance();
        String month = rightNow.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day = rightNow.get(rightNow.DAY_OF_MONTH);
        int year = rightNow.get(rightNow.YEAR);
        return month + " " + day + ", " + year;
    }

}
