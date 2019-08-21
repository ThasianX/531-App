package com.example.a531app.cyclenavigation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.a531app.R;
import com.example.a531app.architecture.AppDatabase;
import com.example.a531app.architecture.LiftModel;
import com.example.a531app.utilities.BaseFragment;
import com.example.a531app.progressnavigation.ProgressFragment;
import com.example.a531app.settingsnavigation.SettingsFragment;
import com.example.a531app.utilities.Program;
import com.example.a531app.setup.SetupActivity;
import com.example.a531app.utilities.Lift;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CycleManager extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {

    private static List<Lift> lifts = new ArrayList<Lift>();
    private int RC_SETUP = 23232;
    public static String LIFT_LIST_KEY = "com.example.a531app.lifts.list";
    public static String PROGRAM_KEY = "com.example.a531app.program";
    public static final String FIRST_LAUNCH_KEY = "com.example.a531app.firstlaunch";
    public static final String SP_NAME = "prefs";
    public static final String LOG_TAG = CycleManager.class.getSimpleName();


    private static Program program;
    private BottomNavigationView bottomNavigationView;

    private AppDatabase mDb;

    public static int overheadId;
    public static int deadliftId;
    public static int benchId;
    public static int squatId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cycle_manager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavBarsetup();

        SharedPreferences sharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        boolean firstLaunch = sharedPreferences.getBoolean(FIRST_LAUNCH_KEY+"temporary to test", true);

        if(firstLaunch){
            mDb = AppDatabase.getDatabase(getApplicationContext());
//            long id = mDb.liftDao().addLift(new LiftModel("Overhead press", 5, 0, 5, 0, 1));
//            overheadId = (int)id;
//            Log.v(LOG_TAG, "overhead id "+overheadId);
//            id = mDb.liftDao().addLift(new LiftModel( "Deadlift",  10, 0, 5, 0, 2));
//            deadliftId = (int) id;
//            Log.v(LOG_TAG, "deadlift id "+deadliftId);
//            id =  mDb.liftDao().addLift(new LiftModel( "Bench press", 5, 0, 5, 0, 4));
//            benchId = (int) id;
//            Log.v(LOG_TAG, "bench id "+benchId);
//            id = mDb.liftDao().addLift(new LiftModel( "Squat", 10, 0, 5, 0, 5));
//            squatId = (int) id;
//            Log.v(LOG_TAG, "squat id "+id);
//
//            Log.v(LOG_TAG, "List size is " + mDb.liftDao().getAllLifts().size());
//            Log.v(LOG_TAG, "1st lift in the list is " + mDb.liftDao().getAllLifts().get(0).getName());
////            assert mDb.liftDao().getLiftById(0).getName().equals("Overhead press") : "Overhead press is in db";
            initialSetup();
        }




    }

    private void initialSetup(){

        lifts.add(new Lift("Overhead press", 1, 5, 0, 5, 0, 1));
        lifts.add(new Lift("Deadlift", 2, 10, 0, 5, 0, 2));
        lifts.add(new Lift("Bench press", 3, 5, 0, 5, 0, 4));
        lifts.add(new Lift("Squat", 4, 10, 0, 5, 0, 5));

        program = new Program("Boring But Big Variation 1");


        Intent intent = new Intent(this, SetupActivity.class);
        intent.putParcelableArrayListExtra(LIFT_LIST_KEY, (ArrayList) lifts);
        startActivityForResult(intent, RC_SETUP);

    }


    private void bottomNavBarsetup(){

//
//        transaction.replace(R.id.fragment_container, new CycleManagerFragment());
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CycleManagerFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()){
                    case R.id.action_cycle:
                        selectedFragment = new CurrentCycleFragment();
                        break;
                    case R.id.action_progress:
                        selectedFragment = new ProgressFragment();
                        break;
                    case R.id.action_settings:
                        selectedFragment = new SettingsFragment();
                        break;


                }
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();

                return true;
            }
        });
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.managermenu, menu);
//        return true;
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
//
//    private void editCycles(){
//
//    }

    public static Program getProgram() {
        return program;
    }

    public static void setProgram(Program newProgram) {
        program = newProgram;
    }
//
//    private void addCycle(){
//        CycleManagerFragment fragment = (CycleManagerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        fragment.addCycle();
//    }

    public static List<Lift> getLifts() {
        return lifts;
    }

    public void setLifts(List<Lift> lifts) {
        this.lifts = lifts;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RC_SETUP && resultCode== Activity.RESULT_OK){
            lifts = data.getParcelableArrayListExtra(LIFT_LIST_KEY);
//            program = data.getParcelableExtra(PROGRAM_KEY);

            bottomNavigationView.setSelectedItemId(R.id.action_cycle);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void changeActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setBackButton(boolean back) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(back);
        getSupportActionBar().setHomeButtonEnabled(back);
    }

}
