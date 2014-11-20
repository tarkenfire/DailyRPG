package com.hinodesoftworks.dailyrpg.todo;


import android.app.Activity;

import java.util.ArrayList;

public class QuestManager {

    private Activity context;
    private ArrayList<Quest> quests;
    private static QuestManager _instance = null;


    public static QuestManager getInstance(Activity context) {
        if (_instance == null) {
            _instance = new QuestManager(context);
        }

        return _instance;
    }

    public QuestManager(Activity context) {
        this.context = context;
        quests = new ArrayList<Quest>();
    }

    public void setQuests(ArrayList<Quest> quests) {
        this.quests = quests;
    }

    public ArrayList<Quest> getQuests(){
        return quests;
    }

    public void addQuest(Quest questToAdd){
        quests.add(questToAdd);
    }

    public void removeQuest(Quest questToRemove){
        quests.remove(questToRemove);
    }

}
