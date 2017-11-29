package com.example.dande.alarm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.example.dande.alarm.Model.Alarm;

import java.util.List;

public class RecyclerViewAlarmAdapter extends RecyclerView.Adapter<RecyclerViewAlarmAdapter.MyHolder> {
    private Context mContext;
    private List<Alarm> alarms;

    public RecyclerViewAlarmAdapter(Context mContext, List<Alarm> alarms) {
        this.mContext = mContext;
        this.alarms = alarms;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_alarm, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final Alarm alarm = alarms.get(position);
        int h = alarm.getH();
        int m = alarm.getM();
        holder.txtTime.setText(((h<10)?"0":"")+h+":"+((m<10)?"0":"")+m);
        String repeat = "";
        if (alarm.getDay().equals("0123456")) repeat = "Hằng ngày";
        else repeat = "Tùy chỉnh";
        if (alarm.getRepeat() == 0) repeat = "Một lần";
        holder.txtRepeat.setText(repeat);
        holder.txtStatus.setText((alarm.getStatus() == 0) ? "Tắt" : "Bật");
        holder.btnSwitch.setChecked((alarm.getStatus() == 1));
        holder.btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyDatabase.getInstance(mContext).onoff(alarm.getId(), b);
                alarm.setStatus((b) ? 1 : 0);
                holder.txtStatus.setText((alarm.getStatus() == 0) ? "Tắt" : "Bật");
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi click vào thì edit, viết EditAlarmActivty rồi thì mở cái comment ở dưới ra
                // Intent intent = new Intent(mContext, EditAlarmActivity.class);
                // Bundle bundle = new Bundle();
                // bundle.putParcelable("data", alarms.get(position));
                // intent.putExtras(bundle);
                // mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (alarms == null) ? 0 : alarms.size();
    }

    public void removeItem(int position) {

        MyDatabase.getInstance(mContext).delete(alarms.get(position).getId());

        Intent intent = new Intent();
        intent.setAction("ALARM_MANAGER_EDIT");
        mContext.sendBroadcast(intent);

        alarms.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, alarms.size());
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtTime;
        TextView txtRepeat;
        TextView txtStatus;
        Switch btnSwitch;

        public MyHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtRepeat = (TextView) itemView.findViewById(R.id.txtRepeat);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            btnSwitch = (Switch) itemView.findViewById(R.id.btnSwitch);


        }
    }
}