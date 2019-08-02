package com.example.a531app.cycles;

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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.a531app.R;
import com.example.a531app.SetupActivity;
import com.example.a531app.appnavigation.BaseFragment;
import com.example.a531app.appnavigation.CycleManagerFragment;
import com.example.a531app.appnavigation.ProgressFragment;
import com.example.a531app.appnavigation.SettingsFragment;
import com.example.a531app.cyclenavigation.CurrentCycleFragment;
import com.example.a531app.cyclenavigation.WeeksDisplayFragment;
import com.example.a531app.lifts.Lift;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CycleManager extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener{

    private static List<Lift> lifts = new ArrayList<Lift>();
    private int RC_SETUP = 23232;
    public static String LIFT_LIST_KEY = "com.example.a531app.lifts.list";
    public static String PROGRAM_KEY = "com.example.a531app.program";

    private static Program program;
    public static String cycleDate;
    public static String todayDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managerSetup();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstLaunch = sharedPreferences.getBoolean("firstLaunchs", true);
        if(firstLaunch){
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

    private void managerSetup(){
        setContentView(R.layout.cycle_manager);
        bottomNavBarsetup();
        todayDate = getDate();
        if(cycleDate==null){
            cycleDate = todayDate;
        }

    }

    private void bottomNavBarsetup(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
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
                transaction.commit();

                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_cycle);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.managermenu, menu);
//        return true;
//    }

    private String getDate(){
        Calendar rightNow = Calendar.getInstance();
        String month = rightNow.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day = rightNow.get(rightNow.DAY_OF_MONTH);
        int year = rightNow.get(rightNow.YEAR);
        return month + " " + day + ", " + year;
    }

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
            program = data.getParcelableExtra(PROGRAM_KEY);
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
