//Katerina Chinnappan
//Homework 1
//KitchenTimerApp
//kachinna@ucsc.edu
//implemented all requirements + extra credit
package com.katerinachinnappan.kitchentimerapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test2017app1";

    // Counter for the number of seconds.
    private int seconds = 0;
    public int timeButton11 = 0; //time for button1
    public int timeButton22 = 0; //time for button2
    public int timeButton33 = 0; //time for button3

    // Countdown timer.
    private CountDownTimer timer = null;

    // One second.  We use Mickey Mouse time.
    private static final int ONE_SECOND_IN_MILLIS = 100;

    //update the buttons (boolean)
    private boolean updateButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTime();
    }

    public void onClickPlus(View v) {
        seconds += 60;
        //update the buttons based on the current time displayed
        updateButton = true;
        displayTime();
    };

    public void onClickMinus(View v) {
        seconds = Math.max(0, seconds - 60);
        //update the buttons based on the current time displayed
        updateButton = true;
        displayTime();
    };

    public void onReset(View v) {
        seconds = 0;
        cancelTimer();
        displayTime();
    }

    //adjust and update the times of each button
    public void timeButtonUpdate()
    {
        //by default, every time button is set to 0. So set the first button to seconds, and the rest are 0
        if(timeButton11 == 0 && timeButton22 == 0 && timeButton33 == 0)
        {
            timeButton33 = timeButton22;
            timeButton22 = timeButton33;
            timeButton11 = seconds;
        }
        //if the first, second or third buttons == to the current seconds, cancel the update
        else if(timeButton11 == seconds || timeButton22 == seconds || timeButton33 == seconds)
        {
            updateButton = false;
        }
        //if none of the buttons are == to the current second, update and shift the buttons from
        //left to right. Set the boolean to true.
        if(timeButton11 != seconds && timeButton22 != seconds && timeButton33 != seconds){
            //for debugging purposes, uncomment to see the log
            /*Log.d(LOG_TAG, "Displaying first time plus" + timeButton11);
            Log.d(LOG_TAG, "Displaying second time plus" + timeButton22);
            Log.d(LOG_TAG, "Displaying third time plus" + timeButton33);
            Log.d(LOG_TAG, "Displaying first seconds" + seconds);*/
            timeButton33 = timeButton22;
            timeButton22 = timeButton11;
            timeButton11 = seconds;
            updateButton = true;
        }
        //call setUp every time to display the current updated buttons
        setTimeButtons(timeButton11, timeButton22, timeButton33);

    }

    //display the time on the buttons
    public void setTimeButtons( int timeButton11, int timeButton22, int timeButton33)
    {
        Button timeButton1 = (Button) findViewById(R.id.time1);
        Button timeButton2 = (Button) findViewById(R.id.time2);
        Button timeButton3 = (Button) findViewById(R.id.time3);

        //convert to minutes and times for according buttons
        int m1 = timeButton11/ 60;
        int s1 = timeButton11 % 60;

        int m2 = timeButton22 / 60;
        int s2 = timeButton22 % 60;

        int m3 = timeButton33 / 60;
        int s3 = timeButton33 % 60;

        //update the time on the the buttons
        timeButton1.setText(String.format("%d:%02d", m1, s1));
        timeButton2.setText(String.format("%d:%02d", m2, s2));
        timeButton3.setText(String.format("%d:%02d", m3, s3));

        Log.d(LOG_TAG, "Displaying first time" + timeButton11);
        Log.d(LOG_TAG, "Displaying second time" + timeButton22);
        Log.d(LOG_TAG, "Displaying third time" + timeButton33);
        //buttons are now updated
        updateButton = false;

    }
    public void onClickStart(View v) {
        if (seconds == 0) {
            cancelTimer();
        }
        if (timer == null) {
            // We create a new timer.
            timer = new CountDownTimer(seconds * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished);
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }

                @Override
                public void onFinish() {
                    seconds = 0;
                    timer = null;
                    displayTime();
                }
            };
            timer.start();

            //if update of button, call to updated the button
            Log.d(LOG_TAG, "Displaying start update " + updateButton);
            if(updateButton == true)
            {
                timeButtonUpdate();
            }
            else  Log.d(LOG_TAG, "Ciao");
        }
    }

    public void onClickStop(View v) {
        cancelTimer();
        displayTime();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Updates the time display.
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        TextView v = (TextView) findViewById(R.id.display);
        int m = seconds / 60;
        int s = seconds % 60;
        v.setText(String.format("%d:%02d", m, s));
        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);
        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }

    /***display the times on the timeButtons***/
    public void displayTime1(View v1)
    {
        TextView v = (TextView) findViewById(R.id.display);

        //set back the time of the button counter to seconds
        seconds = timeButton11;
        int m = seconds / 60;
        int s = seconds % 60;

        v.setText(String.format("%d:%02d", m, s));
        //call start immediately to start the timer and cancel the current time immediately
        cancelTimer();
        onClickStart(v1);
    }

    public void displayTime2(View v1)
    {
        TextView v = (TextView) findViewById(R.id.display);

        //set back the time of the button counter to seconds
        seconds = timeButton22;
        int m = seconds / 60;
        int s = seconds % 60;

        v.setText(String.format("%d:%02d", m, s));
        //call start immediately to start the timer and cancel the current time immediately
        cancelTimer();
        onClickStart(v1);
    }

    public void displayTime3(View v1)
    {
        TextView v = (TextView) findViewById(R.id.display);

        //set back the time of the button counter to seconds
        seconds = timeButton33;
        int m = seconds / 60;
        int s = seconds % 60;

        v.setText(String.format("%d:%02d", m, s));
        //call start immediately to start the timer and cancel the current time immediately
        cancelTimer();
        onClickStart(v1);
    }
}
