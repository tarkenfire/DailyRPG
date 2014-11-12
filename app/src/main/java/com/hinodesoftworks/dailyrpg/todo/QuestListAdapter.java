package com.hinodesoftworks.dailyrpg.todo;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuestListAdapter extends ArrayAdapter <Quest>{
    private Activity context;
    private ArrayList<Quest> quests;



    public QuestListAdapter(Context context, int resource, List<Quest> objects){
        super(context, resource, objects);
    }
}
