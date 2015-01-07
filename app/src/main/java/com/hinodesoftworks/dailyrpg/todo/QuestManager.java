package com.hinodesoftworks.dailyrpg.todo;


import android.app.Activity;

import java.util.ArrayList;
import java.util.Calendar;

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

    public void checkForOverdue(){
        for (Quest q : quests){
            long intMils = q.getDueTimeInMillis();
            Calendar c = Calendar.getInstance();
            long curMils = c.getTimeInMillis();

            if (intMils < curMils ){
                q.setOverdue(true);
            }
        }
    }


    public Quest getQuest(int position){
        return quests.get(position);
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



    public void completeQuest(int position){
        Quest holder = quests.get(position);

        if (holder.getCurrentType() == Quest.QuestType.QUEST_SINGLE){
            quests.remove(position);
        }
        else if (holder.getCurrentType() == Quest.QuestType.QUEST_DAILY){
            //just add 24 hours to quest

            long curMils = holder.getDueTimeInMillis();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(curMils);
            c.add(Calendar.HOUR_OF_DAY, 24);
            holder.setDueTimeInMillis(c.getTimeInMillis());
        }
        else if (holder.getCurrentType() == Quest.QuestType.QUEST_MONTHLY){
            long curMils = holder.getDueTimeInMillis();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(curMils);
            c.add(Calendar.MONTH, 1);
            holder.setDueTimeInMillis(c.getTimeInMillis());
        }

    }

}
