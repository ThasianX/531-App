package com.example.a531app.utilities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a531app.R;
import com.example.a531app.architecture.LiftListViewModel;
import com.example.a531app.architecture.LiftModel;

import java.util.List;

import static com.example.a531app.setup.SetupActivity.EDIT_MAX_KEY;

public class UpdateTrainingMax extends AppCompatActivity {


    private TextView mLiftLabel;
    private EditText mEditMax;
    private Button mUpdate;
    private String name;
    private double max;
    private int liftPos;
    private TextView mNewMax;

    private EditText mReps;
    private EditText mWeight;
    private List<LiftModel> liftModels;
    private LiftModel lift;

    private LiftListViewModel model;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_max);

        getSupportActionBar().setTitle("Update Training Max");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mLiftLabel = findViewById(R.id.lift_label);
        mEditMax = findViewById(R.id.et_max);
        mUpdate = findViewById(R.id.btn_update);
        mReps = findViewById(R.id.reps_input);
        mWeight = findViewById(R.id.weight_input);
        mNewMax = findViewById(R.id.tv_new_max);

        Intent intent = getIntent();
        liftPos = intent.getIntExtra(EDIT_MAX_KEY, -1);

        model = ViewModelProviders.of(this).get(LiftListViewModel.class);
        liftModels = model.getLiftModels();

        lift = liftModels.get(liftPos);

        name = lift.getName();
        max = lift.getTraining_max();

        mLiftLabel.setText(name);
        mEditMax.setHint(""+max+" lb");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.updatemaxmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                onBackPressed();
                return true;

            case R.id.action_save:
                save();

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void updateLift(View v){
        if(!mReps.getText().toString().equals("")&& !mWeight.getText().toString().equals("")){
            int reps = Integer.valueOf(mReps.getText().toString());
            double weight = Double.valueOf(mWeight.getText().toString());
            double estimateRM = weight*reps*.0333+weight;
            double roundTo = lift.getRound_to();
            double max = round(estimateRM, roundTo);

            mNewMax.setText(""+max+" lb");
            mEditMax.setText("" + max);
        } else {
            Toast.makeText(this, "Please input a valid number", Toast.LENGTH_SHORT).show();
        }

    }

    private double round(double value, double roundTo){
        return (double) Math.round(value/roundTo) * roundTo;
    }

    private void save(){
        String max = mEditMax.getText().toString();
        if(!max.equals("")) {
            double unroundedMax = Double.valueOf(max);
            double roundedMax = round(unroundedMax, lift.getRound_to());
            lift.setTraining_max(roundedMax);
            model.updateMax(lift);
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();

        } else {
            Toast.makeText(this, "Please input a valid number", Toast.LENGTH_SHORT).show();
        }
    }



}
