package com.example.dande.alarm;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.dande.alarm.Model.Alarm;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmFragment extends MyFragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Alarm> alarms;
    private RecyclerViewAlarmAdapter alarmAdapter;
    private TextView txtCurrentTime;
    private TextView txtAdd;

    private String sm = " ";
    public AlarmFragment() {
        setName("Báo Thức");
        alarms = new ArrayList<>();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 500);
                Calendar calendar = Calendar.getInstance();
                int h = calendar.get(Calendar.HOUR_OF_DAY);
                int m = calendar.get(Calendar.MINUTE);
                int s = calendar.get(Calendar.SECOND);
                String hh = (h<10)?"0"+h:h+"";
                String mm = (m<10)?"0"+m:m+"";
                String ss = (s<10)?"0"+s:s+"";
                sm = (sm.equals(":"))?" ":":";
                txtCurrentTime.setText(hh + ":" + mm + sm + ss);

            }
        }, 500);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alarm_fragment, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        alarmAdapter = new RecyclerViewAlarmAdapter(getContext(), alarms);
        mRecyclerView.setAdapter(alarmAdapter);

        txtCurrentTime = (TextView) view.findViewById(R.id.txtCurrentTime);

        txtAdd = (TextView) view.findViewById(R.id.txtAdd);
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Khi click vào nút add thì start cái AddAlarmActivty ở đây

            }
        });
        initSwipe();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        alarms.clear();
        alarms.addAll(MyDatabase.getInstance(getContext()).getAll());
        alarmAdapter.notifyDataSetChanged();

    }
    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                alarmAdapter.removeItem(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

}