package com.example.a531app.daysnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a531app.R;
import com.example.a531app.settingsnavigation.SettingsFragment;
import com.example.a531app.utilities.Timer;
import com.example.a531app.utilities.WeekPercentages;
import com.example.a531app.utilities.Lift;

import static android.content.Context.MODE_PRIVATE;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayAdapterViewHolder> {

    private final Lift lift;
    private final Context mContext;
    private final int week;
    private final int[] coresetPercentages;
    private final int[] secondarysetPercetanges;
    private final int[] coresetReps;

    public static String TIMER_ENABLED_KEY = "com.example.a531app.lift";

    public static boolean coreEnabled;
    public static boolean secondaryEnabled;
    public static boolean assistanceEnabled;





    public DayAdapter(Lift lift, int week, String secondary, Context context){
        this.lift = lift;
        this.week = week;
        mContext = context;
        WeekPercentages percentages = new WeekPercentages();
        coresetPercentages = percentages.getCoresetPercentages1();
        secondarysetPercetanges = percentages.getSecondarysetPercentages();
        coresetReps = percentages.getCoresetReps();

        setUpEnabledTimers();

    }

    private void setUpEnabledTimers(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefs", MODE_PRIVATE);
        coreEnabled = sharedPreferences.getBoolean(SettingsFragment.CORE_CHECKED_KEY, true);
        secondaryEnabled= sharedPreferences.getBoolean(SettingsFragment.SECONDARY_CHECKED_KEY, true);
        assistanceEnabled = sharedPreferences.getBoolean(SettingsFragment.ASSISTANCE_CHECKED_KEY, true);
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
    public void onBindViewHolder(@NonNull DayAdapterViewHolder dayAdapterViewHolder, int i) {


        if(i<3) {
            dayAdapterViewHolder.setLabel.setText(coresetReps[(week-1)*3+i] + " reps at " + round((coresetPercentages[(week-1)*3+i] * 0.01 * lift.getTraining_max()), lift.getRound_to())  + " lb");
            dayAdapterViewHolder.setPercetagesLabel.setText(coresetPercentages[(week-1)*3+i] + "% of training max");
            if(i==0){
                dayAdapterViewHolder.setName.setText(lift.getName() + " - 5/3/1");
            } else if(i==2){
                if(week==4){
                    dayAdapterViewHolder.previousPR.setVisibility(View.GONE);
                } else {
                    dayAdapterViewHolder.previousPR.setVisibility(View.VISIBLE);
                    dayAdapterViewHolder.previousPR.setText("PR to beat: " + lift.getPersonal_record() + " reps");
                }
            }
        } else if(i<8){
            dayAdapterViewHolder.setLabel.setText("10 reps at " + round((secondarysetPercetanges[i-3]*0.01*lift.getTraining_max()), lift.getRound_to()) + " lb");
            dayAdapterViewHolder.setPercetagesLabel.setText(secondarysetPercetanges[i-3] + "% of training max");
            dayAdapterViewHolder.previousPR.setVisibility(View.GONE);
            if(i==3){
                dayAdapterViewHolder.setName.setText(lift.getName() + " - Secondary");
            }
        } else {
            dayAdapterViewHolder.setPercetagesLabel.setVisibility(View.GONE);
            dayAdapterViewHolder.setLabel.setText("10 reps");
            if(i==8){
                dayAdapterViewHolder.setName.setText("Lat work - Assistance");
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

            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        int position = getAdapterPosition();
                        Intent intent = new Intent(mContext, Timer.class);
                        if(position>=0 && position<3 && coreEnabled) {
                            mContext.startActivity(intent);
                        } else if(position>=3 && position<8 && secondaryEnabled){
                            mContext.startActivity(intent);
                        } else if(position>=8 && assistanceEnabled){
                            mContext.startActivity(intent);
                        }

                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position==2){

            }
            if(selectionState.isChecked()){
                selectionState.setChecked(false);
            } else {
                if(position==2){
                    final View view = LayoutInflater.from(mContext).inflate(R.layout.pr_alert_dialog, null);
                    final AlertDialog alert = new AlertDialog.Builder(mContext).create();
                    alert.setTitle("New PR");
                    alert.setCancelable(false);


                    final EditText etPr = view.findViewById(R.id.et_pr);

                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int newPr = Integer.valueOf(etPr.getText().toString());
                            if(newPr>lift.getPersonal_record()) {
                                lift.setPersonal_record(newPr);
                                previousPR.setText("Achieved a new rep max PR of " + lift.getPersonal_record() + " reps");
                            } else {
                                previousPR.setText("Good effort but not enough to beat your rep max PR of " + lift.getPersonal_record() + " reps");
                            }
                            selectionState.setChecked(true);
                        }
                    });

                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert.dismiss();
                        }
                    });

                    alert.setView(view);
                    alert.show();
                } else {
                    selectionState.setChecked(true);
                }

            }
        }
    }
}
