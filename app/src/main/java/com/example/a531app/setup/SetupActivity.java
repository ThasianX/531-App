package com.example.a531app.setup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.crashlytics.android.Crashlytics;
import com.example.a531app.R;
import com.example.a531app.utilities.UpdateTrainingMax;
import com.example.a531app.cyclenavigation.CycleManager;
import com.example.a531app.utilities.Program;
import com.example.a531app.utilities.Lift;
import com.example.a531app.settingsnavigation.SelectableAdapter;

import java.util.ArrayList;
import java.util.List;

public class SetupActivity extends AppCompatActivity implements SelectableAdapter.SelectableAdapterClickListener{

    private RecyclerView recyclerView;
    private SelectableAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Lift> lifts;

    private AppCompatSpinner mProgram;
    private AppCompatSpinner mRoundTo;

    private AppCompatSpinner overheadProgress;
    private AppCompatSpinner deadliftProgress;
    private AppCompatSpinner benchProgress;
    private AppCompatSpinner squatProgress;

    private static int RC_EDIT_LIFT = 1234;
    public static String EDIT_MAX_KEY = "com.example.a531app.edit";
    private int editPosition;

    private String programName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
//
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        myToolbar.setTitle("Setup");
//        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("Setup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.rv_maxes);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        lifts = intent.getParcelableArrayListExtra(CycleManager.LIFT_LIST_KEY);
        adapter = new SelectableAdapter(this, this, lifts);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        mProgram = findViewById(R.id.program_spinner);
        mRoundTo = findViewById(R.id.round_to_spinner);

        overheadProgress = findViewById(R.id.overhead_spinner);
        deadliftProgress = findViewById(R.id.deadlift_spinner);
        benchProgress = findViewById(R.id.bench_spinner);
        squatProgress = findViewById(R.id.squat_spinner);

        setSpinners();

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

        double value = lifts.get(0).getRound_to();
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.round_to_array, android.R.layout.simple_spinner_item);
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
                if(!value.equals(""+lifts.get(0).getRound_to())){
                    Crashlytics.log("Round to nearest value changed to "+value);
                    double round = Double.valueOf(value);
                    adapter.changeRoundTo(round);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = lifts.get(0).getProgression();
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.progression_array, android.R.layout.simple_spinner_item);
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
                if(!value.equals(""+lifts.get(0).getProgression())){
                    Crashlytics.log("Overhead progress changed to " + value);
                    double progression = Double.valueOf(value);
                    adapter.changeProgression(progression, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = lifts.get(1).getProgression();
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.progression_array, android.R.layout.simple_spinner_item);
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
                if(!value.equals(""+lifts.get(1).getProgression())){
                    Crashlytics.log("Deadlift progress changed to " + value);
                    double progression = Double.valueOf(value);
                    adapter.changeProgression(progression, 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = lifts.get(2).getProgression();
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.progression_array, android.R.layout.simple_spinner_item);
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
                if(!value.equals(""+lifts.get(2).getProgression())){
                    Crashlytics.log("Bench press progress changed to " + value);
                    double progression = Double.valueOf(value);
                    adapter.changeProgression(progression, 2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value = lifts.get(3).getProgression();
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.progression_array, android.R.layout.simple_spinner_item);
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
                if(!value.equals(""+lifts.get(3).getProgression())){
                    Crashlytics.log("Squat progress changed to " + value);
                    double progression = Double.valueOf(value);
                    adapter.changeProgression(progression, 3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setupmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId==R.id.action_done){
            SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstLaunch", false);
            editor.apply();

            Program program = new Program(programName);


            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(CycleManager.LIFT_LIST_KEY, (ArrayList) lifts);
            intent.putExtra(CycleManager.PROGRAM_KEY, programName);
            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Lift lift, int position) {
        editPosition = position;
        Intent intent = new Intent(this, UpdateTrainingMax.class);
        intent.putExtra(EDIT_MAX_KEY, lift);
        startActivityForResult(intent, RC_EDIT_LIFT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RC_EDIT_LIFT && resultCode== Activity.RESULT_OK){
            Lift lift = data.getParcelableExtra(EDIT_MAX_KEY);
            adapter.changeLift(lift.getTraining_max(), editPosition);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
