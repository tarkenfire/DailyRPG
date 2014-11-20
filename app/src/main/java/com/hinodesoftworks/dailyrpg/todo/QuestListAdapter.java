package com.hinodesoftworks.dailyrpg.todo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hinodesoftworks.dailyrpg.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestListAdapter extends ArrayAdapter<Quest> {
    private Context context;
    private ArrayList<Quest> quests;


    public QuestListAdapter(Context context, int resource, ArrayList<Quest> quests) {
        super(context, resource, quests);

        this.context = context;
        this.quests = quests;

        Log.e("HI LIST", "Size: " + quests.size());
    }

    @Override
    public void add(Quest object) {
        quests.add(object);
    }

    @Override
    public void clear() {
        quests.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.quest_row, parent, false);
        }

        Quest holder = quests.get(position);

        LinearLayout container = (LinearLayout)convertView.findViewById(R.id.quest_row_container);
        TextView titleView = (TextView)convertView.findViewById(R.id.quest_row_name);
        TextView dateView = (TextView)convertView.findViewById(R.id.quest_row_time);

        titleView.setText(holder.getQuestName());

        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        Calendar formatCalendar = Calendar.getInstance();
        formatCalendar.setTimeInMillis(holder.getDueTimeInMillis());

        dateView.setText("Due: " + df.format(formatCalendar.getTime()));

        //change list item color based upon type of quest
        switch (holder.getCurrentType()){
            case QUEST_SINGLE:
                container.setBackgroundColor(Color.parseColor("#FF8071"));
                break;
            case QUEST_DAILY:
                container.setBackgroundColor(Color.parseColor("#98FF90"));
                break;
            case QUEST_MONTHLY:
                container.setBackgroundColor(Color.parseColor("#DEA4FF"));
                break;

        }

        return convertView;
    }
}
