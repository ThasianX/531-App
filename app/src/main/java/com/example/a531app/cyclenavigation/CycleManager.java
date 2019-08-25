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
import android.view.MenuItem;

import com.example.a531app.R;
import com.example.a531app.architecture.AppDatabase;
import com.example.a531app.utilities.BaseFragment;
import com.example.a531app.notesnavigation.NotesFragment;
import com.example.a531app.settingsnavigation.SettingsFragment;
import com.example.a531app.utilities.Program;
import com.example.a531app.setup.SetupActivity;

public class CycleManager extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {

    private int RC_SETUP = 23232;
    public static String LIFT_LIST_KEY = "com.example.a531app.lifts.list";
    public static String PROGRAM_KEY = "com.example.a531app.program";
    public static final String FIRST_LAUNCH_KEY = "com.example.a531app.firstlaunch";
    public static final String SP_NAME = "prefs";
    public static final String LOG_TAG = CycleManager.class.getSimpleName();


    private static Program program;
    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cycle_manager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavBarsetup();

        bottomNavigationView.setSelectedItemId(R.id.action_cycle);

        SharedPreferences sharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        boolean firstLaunch = sharedPreferences.getBoolean(FIRST_LAUNCH_KEY, true);

        program = new Program("Boring But Big Variation 1");

        if(firstLaunch){
            Intent intent = new Intent(this, SetupActivity.class);
            startActivityForResult(intent, RC_SETUP);
        }


    }

    private void bottomNavBarsetup(){

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
                        selectedFragment = new NotesFragment();
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
    public static Program getProgram() {
        return program;
    }

    public static void setProgram(Program newProgram) {
        program = newProgram;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RC_SETUP && resultCode== Activity.RESULT_OK){
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
