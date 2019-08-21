package com.example.a531app.settingsnavigation;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.a531app.R;
import com.example.a531app.architecture.LiftListViewModel;
import com.example.a531app.architecture.LiftModel;
import com.example.a531app.cyclenavigation.CycleManager;
import com.example.a531app.daysnavigation.DayAdapter;
import com.example.a531app.utilities.Program;
import com.example.a531app.utilities.BaseFragment;
import com.example.a531app.utilities.Lift;
import com.example.a531app.utilities.UpdateTrainingMax;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment implements SelectableAdapter.SelectableAdapterClickListener {


    private RecyclerView recyclerView;
    private SelectableAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private AppCompatSpinner mProgram;
    private AppCompatSpinner mRoundTo;

    private AppCompatSpinner overheadProgress;
    private AppCompatSpinner deadliftProgress;
    private AppCompatSpinner benchProgress;
    private AppCompatSpinner squatProgress;

    private List<LiftModel> liftModels;

    private static int RC_EDIT_LIFT = 1234;
    public static final String EDIT_MAX_KEY = "com.example.a531app.edit";
    private int editPosition;

    private CheckBox coreCb;
    private CheckBox secondaryCb;
    private CheckBox assistanceCb;

    public static final String CORE_CHECKED_KEY = "com.example.a531app.core";
    public static final String SECONDARY_CHECKED_KEY = "com.example.a531app.secondary";
    public static final String ASSISTANCE_CHECKED_KEY = "com.example.a531app.assistance";

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        LiftListViewModel model = ViewModelProviders.of(this).get(LiftListViewModel.class);
        liftModels = model.getLiftModels();

        recyclerView = view.findViewById(R.id.rv_maxes);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SelectableAdapter(getActivity(), this, liftModels);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        mProgram = view.findViewById(R.id.program_spinner);
        mRoundTo = view.findViewById(R.id.round_to_spinner);
        overheadProgress = view.findViewById(R.id.overhead_spinner);
        deadliftProgress = view.findViewById(R.id.deadlift_spinner);
        benchProgress = view.findViewById(R.id.bench_spinner);
        squatProgress = view.findViewById(R.id.squat_spinner);
        setSpinners();

        coreCb = view.findViewById(R.id.cb_core);
        secondaryCb = view.findViewById(R.id.cb_secondary);
        assistanceCb = view.findViewById(R.id.cb_assistance);
        setCbTimers();

        return view;
    }

    private void setCbTimers(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        coreCb.setChecked(sharedPreferences.getBoolean(CORE_CHECKED_KEY, true));
        secondaryCb.setChecked(sharedPreferences.getBoolean(SECONDARY_CHECKED_KEY, true));
        assistanceCb.setChecked(sharedPreferences.getBoolean(ASSISTANCE_CHECKED_KEY, true));

        coreCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(CORE_CHECKED_KEY, isChecked);
                editor.apply();

                if(isChecked){
                    DayAdapter.coreEnabled = true;
                } else {
                    DayAdapter.coreEnabled = false;
                }
            }
        });

        secondaryCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SECONDARY_CHECKED_KEY, isChecked);
                editor.apply();
                if(isChecked){
                    DayAdapter.secondaryEnabled = true;
                } else {
                    DayAdapter.secondaryEnabled = false;
                }
            }
        });

        assistanceCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(ASSISTANCE_CHECKED_KEY, isChecked);
                editor.apply();
                if(isChecked){
                    DayAdapter.assistanceEnabled = true;
                } else {
                    DayAdapter.assistanceEnabled = false;
                }
            }
        });
    }

    private void setSpinners(){

        mProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                CycleManager.setProgram(new Program(value));
                //Probably want to do this after making a database
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        double value = lifts.get(0).getRound_to();
//        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.round_to_array, android.R.layout.simple_spinner_item);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mRoundTo.setAdapter(spinnerAdapter);
//        int position = spinnerAdapter.getPosition(value+" lb");
//        mRoundTo.setSelection(position);

        double value = liftModels.get(0).getRound_to();
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.round_to_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoundTo.setAdapter(spinnerAdapter);
        int position = spinnerAdapter.getPosition(value+" lb");
        mRoundTo.setSelection(position);

        mRoundTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                int lbPos = value.indexOf(" lb");
                value = value.substring(0, lbPos);
                if(!value.equals(""+liftModels.get(0).getRound_to())){
                    double round = Double.valueOf(value);
                    liftModels.get(0).setRound_to(round);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = liftModels.get(0).getProgression();
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.progression_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        overheadProgress.setAdapter(spinnerAdapter);
        position = spinnerAdapter.getPosition(value+" lb");
        overheadProgress.setSelection(position);

        overheadProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                int lbPos = value.indexOf(" lb");
                value = value.substring(0, lbPos);
                if(!value.equals(""+liftModels.get(0).getProgression())){
                    double progression = Double.valueOf(value);
                    liftModels.get(0).setProgression(progression);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = liftModels.get(1).getProgression();
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.progression_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deadliftProgress.setAdapter(spinnerAdapter);
        position = spinnerAdapter.getPosition(value+" lb");
        deadliftProgress.setSelection(position);

        deadliftProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                int lbPos = value.indexOf(" lb");
                value = value.substring(0, lbPos);
                if(!value.equals(""+liftModels.get(1).getProgression())){
                    double progression = Double.valueOf(value);
                    liftModels.get(1).setProgression(progression);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = liftModels.get(2).getProgression();
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.progression_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        benchProgress.setAdapter(spinnerAdapter);
        position = spinnerAdapter.getPosition(value+" lb");
        benchProgress.setSelection(position);

        benchProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                int lbPos = value.indexOf(" lb");
                value = value.substring(0, lbPos);
                if(!value.equals(""+liftModels.get(2).getProgression())){
                    double progression = Double.valueOf(value);
                    liftModels.get(2).setProgression(progression);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = liftModels.get(3).getProgression();
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.progression_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        squatProgress.setAdapter(spinnerAdapter);
        position = spinnerAdapter.getPosition(value+" lb");
        squatProgress.setSelection(position);

        squatProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                int lbPos = value.indexOf(" lb");
                value = value.substring(0, lbPos);
                if(!value.equals(""+liftModels.get(3).getProgression())){
                    double progression = Double.valueOf(value);
                    liftModels.get(3).setProgression(progression);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public String actionTitle() {
        return "Settings";

    }

    @Override
    public boolean setBackButton() {
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onClick(LiftModel lift, int position) {
        editPosition = position;
        Intent intent = new Intent(getActivity(), UpdateTrainingMax.class);
        intent.putExtra(EDIT_MAX_KEY, editPosition);
        startActivity(intent);
    }

}
