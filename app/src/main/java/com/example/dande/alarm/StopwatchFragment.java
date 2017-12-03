package com.example.dande.alarm;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StopwatchFragment extends MyFragment{
    Button btnStart,btnPause, btnLap;
    TextView txtTimer;
    Handler customHandler = new Handler();
    LinearLayout container;
    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeMilliseconds=SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff+timeMilliseconds;
            int secs=(int)(updateTime/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(updateTime%1000);
            txtTimer.setText(""+mins+":"+String.format("%2d",secs)+":"
                                        +String.format("%3d",milliseconds));
            customHandler.postDelayed(this,0);
        }
    };
    long startTime=0L, timeMilliseconds=0L, timeSwapBuff=0L, updateTime=0L;
    public StopwatchFragment(){
        setName("Bấm Giờ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_stopwatch_fragment, null);

        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnPause = (Button) view.findViewById(R.id.btnPause);
        btnLap   = (Button) view.findViewById(R.id.btnLap);
        txtTimer = (TextView) view.findViewById(R.id.timerValue);
        container = (LinearLayout) view.findViewById(R.id.container);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();

                customHandler.postDelayed(updateTimerThread,0);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff=timeMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });

        final ViewGroup finalContainer = container;
        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row,null);
                TextView txtValue = (TextView)addView.findViewById(R.id.txtContent);
                txtValue.setText(txtTimer.getText());
                finalContainer.addView(addView);
            }
        });
        return view;
    }
    }

