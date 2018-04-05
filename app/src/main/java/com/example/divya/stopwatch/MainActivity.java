package com.example.divya.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Earlier I have used chronometer for the stopwatch which didn't function well.
    // Concepts of handler, runnable thread are used here.
    Button button_Start,button_Pause,button_Reset,button_Lap;
    TextView textView;
    ListView listView_Lap;
    String str_lap[] = new String[]{};
    List<String> list_lap;
    ArrayAdapter<String> adapter;
    Handler handler;
    long start = 0L;
    long TimeBuff, UpdateTime = 0L ;
    long msTim = 0L;
    int min,sec,hr;
    private static final String TAG = "MyActivity";
    int i = 0; // for no. of laps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_Start = (Button) findViewById(R.id.button_Start);
        button_Lap = (Button) findViewById(R.id.button_Lap);
        button_Pause = (Button) findViewById(R.id.button_Pause);
        button_Reset = (Button) findViewById(R.id.button_Reset);
        listView_Lap = (ListView) findViewById(R.id.listView_Lap);
        textView = (TextView) findViewById(R.id.textView);
        list_lap = new ArrayList<>(Arrays.asList(str_lap));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_lap);
        handler = new Handler();
        listView_Lap.setAdapter(adapter);
        button_Pause.setVisibility(View.GONE);
        button_Reset.setVisibility(View.GONE);
        button_Lap.setVisibility(View.GONE);
        button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* System.currentTimeMillis() returns the difference, measured in milliseconds, between the current time and midnight, January 1, 1970 UTC */
                start = SystemClock.uptimeMillis();
                handler.postDelayed(r,1000); // 0 is the no. of miliseconds after which activity will be updated.
                button_Reset.setVisibility(View.VISIBLE);
                button_Lap.setVisibility(View.VISIBLE);
                button_Pause.setVisibility(View.VISIBLE);
                button_Start.setVisibility(View.GONE);
            }
        });

        button_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset click is supposed to do : textview=>00:00:00 , reset all laps, all datas stored .
                //textView.setText(String.format("%2d",00)+":"+(String.format("%2d",00))+":"+String.format("%2d",00));
                min=0;
                sec=0;
                hr=0;
                i=0;
                start=0L;
                msTim=0L;
                UpdateTime=0L;
                TimeBuff=0L;
                list_lap.clear();
                adapter.notifyDataSetChanged();
                button_Start.setVisibility(View.VISIBLE);
                button_Pause.setVisibility(View.GONE);
                button_Lap.setVisibility(View.GONE);
                button_Reset.setVisibility(View.GONE);
                handler.removeCallbacks(r);
                textView.setText("" + 00 + ":" + String.format("%02d", 00) + ":" + String.format("%02d", 00));
                //start = SystemClock.uptimeMillis();

                //Used whenadd(), insert(), remove(), and clear() is used.When an ArrayAdapter is constructed, it holds the reference for the List that was passed in. If you were to pass in a List
                //that was a member of an Activity, and change that Activity member later, the ArrayAdapter is still holding a reference to the
                //original List. The Adapter does not know you changed the List in the Activity.
            }
        });

        button_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBuff+=msTim;
                handler.removeCallbacks(r);
                button_Start.setVisibility(View.VISIBLE);
                button_Reset.setVisibility(View.VISIBLE);
                button_Pause.setVisibility(View.GONE);
                button_Lap.setVisibility(View.GONE);

            }
        });

        button_Lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                list_lap.add("Lap "+ i +" "+ textView.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
       /*
        chronometer_id = (Chronometer) findViewById(R.id.chronometer_id);
        button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer_id.setFormat("00:%s");
                chronometer_id.start();
            }
        });
        // for resume
        button_Resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer_id.start();
            }
        });

        button_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer_id.stop();
            }
        });

        button_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer_id.setFormat(null);
            }
        });*/
    }

    public Runnable r = new Runnable() {
        public void run() {
            msTim = SystemClock.uptimeMillis() - start;
            //uptimeMillis() is counted in milliseconds since the system was booted. This clock stops when the system enters deep
            // sleep (CPU off, display dark, device waiting for external input)
            UpdateTime = TimeBuff + msTim;
            /*
            sec = (int) (UpdateTime / 1000);
            min = sec / 60;
            sec = sec % 60;
            ms = (int) (UpdateTime % 1000);
            */
            sec = (int) UpdateTime/1000;
            min = sec/60;
            hr = min/60;
            sec = sec%60;
            min = min%60;
            textView.setText(String.format("%02d", hr) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
            handler.postDelayed(this,1000);
            /* We can also use this.
            String time =  hours + ":" + minutes + ":" + seconds;
            textView.setText(time); */
        }

    };
}
