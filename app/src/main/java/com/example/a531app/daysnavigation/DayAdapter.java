package com.example.a531app.daysnavigation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a531app.R;
import com.example.a531app.architecture.LiftListViewModel;
import com.example.a531app.architecture.LiftModel;
import com.example.a531app.cyclenavigation.CurrentCycleFragment;
import com.example.a531app.cyclenavigation.CycleManager;
import com.example.a531app.settingsnavigation.SettingsFragment;
import com.example.a531app.utilities.Timer;
import com.example.a531app.utilities.WeekPercentages;
import com.example.a531app.utilities.Lift;

import static android.content.Context.MODE_PRIVATE;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayAdapterViewHolder> {

    private final LiftModel lift;
    private final Context mContext;
    private final int week;
    private final int[] coresetPercentages;
    private final int[] secondarysetPercetanges;
    private final int[] coresetReps;

    private boolean coreEnabled;
    private boolean secondaryEnabled;
    private boolean assistanceEnabled;

    private static final String LOG_TAG = DayAdapter.class.getSimpleName();

    private boolean checkedSetup = true;

    private LiftModel secondary;
    private String assistance;

    private LiftListViewModel model;

//    private SparseBooleanArray itemStateArray = new SparseBooleanArray();


    public DayAdapter(LiftModel lift, int week, String secondary, Context context, LiftListViewModel model){
        this.lift = lift;
        this.week = week;
        mContext = context;

        WeekPercentages percentages = new WeekPercentages();
        coresetPercentages = percentages.getCoresetPercentages1();
        secondarysetPercetanges = percentages.getSecondarysetPercentages();
        coresetReps = percentages.getCoresetReps();

        this.model = model;

        this.secondary = model.getLiftByName(secondary);
        this.assistance = lift.getAssistance();

        setUpEnabledTimers();

    }

    private void setUpEnabledTimers(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CycleManager.SP_NAME, MODE_PRIVATE);
        coreEnabled = sharedPreferences.getBoolean(SettingsFragment.CORE_CHECKED_KEY, true);
        secondaryEnabled= sharedPreferences.getBoolean(SettingsFragment.SECONDARY_CHECKED_KEY, true);
        assistanceEnabled = sharedPreferences.getBoolean(SettingsFragment.ASSISTANCE_CHECKED_KEY, true);
    }

    private boolean isChecked(int position){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CycleManager.SP_NAME, MODE_PRIVATE);
        boolean checked = sharedPreferences.getBoolean("CB"+week+lift.getDay()+ position, false);
        Log.v(LOG_TAG, "SP Checkbox #" + position + " for week " + week + " for day " + lift.getDay() + " is "+checked);
        return checked;
//        return itemStateArray.get(position, false);
    }

    private void setChecked(int position, boolean checked){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CycleManager.SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("CB"+week+lift.getDay()+position, checked);



        Log.v(LOG_TAG, "Setting SP checkbox #"+position + "for week " + week + " for day " + lift.getDay() + " to " + checked);
        editor.apply();
//        itemStateArray.put(position, checked);
    }

    private void editProgress(boolean checked){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CycleManager.SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int current;

        if(checked){
            switch (week){
                case 1:
                    current = sharedPreferences.getInt(CurrentCycleFragment.WEEK_1_KEY, 0);
                    editor.putInt(CurrentCycleFragment.WEEK_1_KEY, ++current);
                    break;
                case 2:
                    current = sharedPreferences.getInt(CurrentCycleFragment.WEEK_2_KEY, 0);
                    editor.putInt(CurrentCycleFragment.WEEK_2_KEY, ++current);
                    break;
                case 3:
                    current = sharedPreferences.getInt(CurrentCycleFragment.WEEK_3_KEY, 0);
                    editor.putInt(CurrentCycleFragment.WEEK_3_KEY, ++current);
                    break;
                case 4:
                    current = sharedPreferences.getInt(CurrentCycleFragment.WEEK_4_KEY, 0);
                    editor.putInt(CurrentCycleFragment.WEEK_4_KEY, ++current);
                    break;
            }
        } else {
            switch (week){
                case 1:
                    current = sharedPreferences.getInt(CurrentCycleFragment.WEEK_1_KEY, 0);
                    editor.putInt(CurrentCycleFragment.WEEK_1_KEY, --current);
                    break;
                case 2:
                    current = sharedPreferences.getInt(CurrentCycleFragment.WEEK_2_KEY, 0);
                    editor.putInt(CurrentCycleFragment.WEEK_2_KEY, --current);
                    break;
                case 3:
                    current = sharedPreferences.getInt(CurrentCycleFragment.WEEK_3_KEY, 0);
                    editor.putInt(CurrentCycleFragment.WEEK_3_KEY, --current);
                    break;
                case 4:
                    current = sharedPreferences.getInt(CurrentCycleFragment.WEEK_4_KEY, 0);
                    editor.putInt(CurrentCycleFragment.WEEK_4_KEY, --current);
                    break;
            }
        }


        editor.apply();
    }

    @NonNull
    @Override
    public DayAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView;
        switch (viewType){
            case 0:
                itemView = LayoutInflater.from(mContext).inflate(R.layout.label_day_row, viewGroup, false);
                return new DayAdapterViewHolder(itemView, viewType);
            default:
                itemView = LayoutInflater.from(mContext).inflate(R.layout.day_row, viewGroup, false);
                return new DayAdapterViewHolder(itemView, viewType);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull DayAdapterViewHolder dayAdapterViewHolder, int position) {

        if(checkedSetup){
            dayAdapterViewHolder.selectionState.setChecked(isChecked(position));
            dayAdapterViewHolder.setCheckedListener();
            if(position==12){
                checkedSetup=false;
            }
        }

        if(position<3) {
            dayAdapterViewHolder.setLabel.setText(coresetReps[(week-1)*3+position] + " reps at " + round((coresetPercentages[(week-1)*3+position] * 0.01 * lift.getTraining_max()), lift.getRound_to())  + " lb");
            dayAdapterViewHolder.setPercetagesLabel.setText(coresetPercentages[(week-1)*3+position] + "% of training max");
            if(position==0){
                dayAdapterViewHolder.setName.setText(lift.getName() + " - 5/3/1");
            } else if(position==2){
                if(week==4){
                    dayAdapterViewHolder.previousPR.setVisibility(View.GONE);
                } else {
                    dayAdapterViewHolder.previousPR.setVisibility(View.VISIBLE);
                    dayAdapterViewHolder.previousPR.setText("PR to beat: " + lift.getPersonal_record() + " reps");
                }
            }
        } else if(position<8){
            dayAdapterViewHolder.setLabel.setText("10 reps at " + round((secondarysetPercetanges[position-3]*0.01*secondary.getTraining_max()), secondary.getRound_to()) + " lb");
            dayAdapterViewHolder.setPercetagesLabel.setText(secondarysetPercetanges[position-3] + "% of training max");
            dayAdapterViewHolder.previousPR.setVisibility(View.GONE);
            if(position==3){
                dayAdapterViewHolder.setName.setText(secondary.getName() + " - Secondary");
            }
        } else {
            dayAdapterViewHolder.setPercetagesLabel.setVisibility(View.GONE);
            dayAdapterViewHolder.setLabel.setText("10 reps");
            if(position==8){
                dayAdapterViewHolder.setName.setText(assistance + " - Assistance");
            }
        }

    }

    private double round(double value, double roundTo){
        return (double) Math.round(value/roundTo) * roundTo;
    }

    @Override
    public int getItemCount() {
        return 13;
    }

    @Override
    public int getItemViewType(int position) {
        if(position ==0 || position==3 || position==8){
            return 0;
        }
        return 1;
    }


    private void startTimer(int position){
        Intent intent = new Intent(mContext, Timer.class);
        if(position>=0 && position<3 && coreEnabled) {
            mContext.startActivity(intent);
        } else if(position>=3 && position<8 && secondaryEnabled){
            mContext.startActivity(intent);
        } else if(position>=8 && assistanceEnabled){
            mContext.startActivity(intent);
        }
    }

    public class DayAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView setLabel;
        private TextView setPercetagesLabel;
        private TextView previousPR;
        private TextView setName;
        private CheckBox selectionState;

        public DayAdapterViewHolder(View view, int viewType){
            super(view);
            setLabel = view.findViewById(R.id.setLabel);
            setPercetagesLabel = view.findViewById(R.id.set_percentages_label);
            selectionState = view.findViewById(R.id.checkBox);
            previousPR = view.findViewById(R.id.tv_pr);

            if(viewType == 0){
                setName = view.findViewById(R.id.tv_set_name);
            }

            view.setOnClickListener(this);

        }

        public void setCheckedListener(){
            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        final int position = getAdapterPosition();
                        setChecked(position, true);
                        editProgress(true);

                        if(position==2) {
                            final View view = LayoutInflater.from(mContext).inflate(R.layout.pr_alert_dialog, null);
                            final AlertDialog alert = new AlertDialog.Builder(mContext).create();
                            alert.setTitle("New PR");
                            alert.setCancelable(false);


                            final EditText etPr = view.findViewById(R.id.et_pr);

                            alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String reps = etPr.getText().toString();
                                    if(!reps.equals("")){
                                        int newPr = Integer.valueOf(etPr.getText().toString());
                                            if (newPr > lift.getPersonal_record()) {
                                                lift.setPersonal_record(newPr);
                                                model.updatePr(lift);
                                                previousPR.setText("Achieved a new rep max PR of " + lift.getPersonal_record() + " reps");
                                            } else {
                                                previousPR.setText("Good effort but not enough to beat your rep max PR of " + lift.getPersonal_record() + " reps");
                                            }
                                        startTimer(position);
                                    }

                                }
                            });

                            alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    selectionState.setChecked(false);
                                    alert.dismiss();
                                }
                            });

                            alert.setView(view);
                            alert.show();
//
//                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
//                                @Override
//                                public void onClick(View v) {
//                                    boolean isNumber;
//                                    int newPr = Integer.valueOf(etPr.getText().toString());
//                                    if (newPr > lift.getPersonal_record()) {
//                                        lift.setPersonal_record(newPr);
//                                        previousPR.setText("Achieved a new rep max PR of " + lift.getPersonal_record() + " reps");
//                                    } else {
//                                        previousPR.setText("Good effort but not enough to beat your rep max PR of " + lift.getPersonal_record() + " reps");
//                                    }
//                                    startTimer(position);
//
//                                }
//                            });
                        } else {
                            startTimer(position);
                        }

                    } else {
                        setChecked(getAdapterPosition(), false);
                        editProgress(false);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(selectionState.isChecked()){
                selectionState.setChecked(false);
            } else {
                selectionState.setChecked(true);
            }
        }
    }
}
