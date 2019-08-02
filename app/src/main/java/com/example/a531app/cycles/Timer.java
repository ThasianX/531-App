package com.example.a531app.cycles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a531app.R;
import com.example.a531app.lifts.Lift;

public class Timer extends Activity {

    private TextView countdown;
    private CountDownTimer cTimer = null;
    private Button subtractTime;
    private Button skipTimer;
    private Button addTime;
    private long countdownPeriod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        countdown = findViewById(R.id.tv_timer_countdown);
        subtractTime = findViewById(R.id.btn_minus);
        addTime = findViewById(R.id.btn_plus);
        skipTimer = findViewById(R.id.btn_skip);
        countdownPeriod = 90000;


        subtractTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractTime();
            }
        });

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTime();
            }
        });

        skipTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipTimer();
            }
        });

        startTimer();

    }



    private void startTimer(){
        cTimer = new CountDownTimer(countdownPeriod, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownPeriod = millisUntilFinished;
                int seconds = (int) (millisUntilFinished/1000);
                int min = seconds/60;
                seconds %= 60;
                countdown.setText(String.format("%02d", min) + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                cancelTimer();
                onBackPressed();
            }
        }.start();
    }

    private void cancelTimer(){
        if(cTimer!=null){
            cTimer.cancel();
        }
    }


    private void addTime(){
        cTimer.cancel();
        countdownPeriod +=15000;
        cTimer = new CountDownTimer(countdownPeriod, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownPeriod = millisUntilFinished;
                int seconds = (int) (millisUntilFinished/1000);
                int min = seconds/60;
                seconds %= 60;
                countdown.setText(String.format("%02d", min) + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                cancelTimer();
                onBackPressed();
            }
        }.start();
    }

    private void subtractTime(){
        cTimer.cancel();
        countdownPeriod -= 15000;
        cTimer = new CountDownTimer(countdownPeriod, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownPeriod = millisUntilFinished;
                int seconds = (int) (millisUntilFinished/1000);
                int min = seconds/60;
                seconds %= 60;
                countdown.setText(String.format("%02d", min) + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                cancelTimer();
                onBackPressed();
            }
        }.start();
    }

    private void skipTimer(){
        cancelTimer();
        onBackPressed();
    }
}
