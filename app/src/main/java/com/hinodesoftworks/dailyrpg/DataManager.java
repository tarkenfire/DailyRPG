package com.hinodesoftworks.dailyrpg;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hinodesoftworks.dailyrpg.game.Character;
import com.hinodesoftworks.dailyrpg.todo.Quest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataManager {

    //uses shared prefs since most values in the game are simple values anyways

    private Context mContext;
    private static DataManager _instance = null;
    private static final String PREF_NAME = "com.hinodesoftworks.dailyrpg.PREFS_FILE_KEY";

    public static DataManager getInstance(Context context){
        if (_instance == null){
            _instance = new DataManager(context);
        }
        return _instance;
    }

    private DataManager(Context context){
        mContext = context;
    }

    public void persistData(Character character, ArrayList<Quest> quests){
        //get shared prefs
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        //character
        editor.putInt("c_player_level", character.getLevel());
        editor.putInt("c_player_exp", character.getExperience());
        editor.putInt("c_base_hp", character.getBaseHP());
        editor.putInt("c_base_atk", character.getBaseAtk());
        editor.putInt("c_base_def", character.getBaseDef());

        editor.putString("c_player_name", character.getName());
        editor.putString("c_player_class_name", character.getClassName());

        //TODO: persist user image

        //quests
        JSONArray container = new JSONArray();
        JSONObject holder = new JSONObject();
        String typeHolder = "";

        for (Quest q : quests){
            //get string for quest type
            switch (q.getCurrentType()){
                case QUEST_DAILY:
                    typeHolder = "DAILY";
                    break;
                case QUEST_MONTHLY:
                    typeHolder = "MONTHLY";
                    break;
                case QUEST_SINGLE:
                    typeHolder = "SINGLE";
                    break;
            }

            //construct json object
            try {
                holder.put("q_name", q.getQuestName());
                holder.put("q_desc", q.getQuestDetails());
                holder.put("q_long_time", q.getDueTimeInMillis());
                holder.put("q_exp_value", q.getExpValue());
                holder.put("q_is_overdue", q.isOverdue());
                holder.put("q_type_string", typeHolder);
            }
            catch (JSONException e){
                e.printStackTrace();
            }



            //add to array
            container.put(holder);
        }

        //add string version of JSON object to editor
        editor.putString("quests", container.toString());

        editor.apply();
    }

    //data accessing methods
    public Character getStoredCharacter(){
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Character holder = new Character(prefs.getString("c_player_name", "Name"),
                prefs.getString("c_player_class_name", "CNAME"), prefs.getInt("c_player_level", 1),
                prefs.getInt("c_base_hp", 100), prefs.getInt("c_base_atk", 40),
                prefs.getInt("c_base_def", 30));

        holder.setExperience(prefs.getInt("c_player_exp", 0));

        return holder;
    }

    public ArrayList<Quest> getStoredQuests(){
        ArrayList<Quest> qHolder = new ArrayList<Quest>();
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        try {
            JSONArray questsJSON = new JSONArray(prefs.getString("quests", ""));

            //TODO: get new object instance creation out of loop
            for (int i = 0; i < questsJSON.hashCode(); i++){
                JSONObject oHolder = questsJSON.getJSONObject(i);
                String typeString = oHolder.getString("q_type_string");

                //get actual enum from string value
                Quest.QuestType typeEnum = Quest.QuestType.QUEST_SINGLE;

                if (typeString.matches("DAILY")){
                    typeEnum = Quest.QuestType.QUEST_DAILY;
                }
                else if (typeString.matches("MONTHLY")){
                    typeEnum = Quest.QuestType.QUEST_MONTHLY;
                }
                //else value is default SINGLE value; already set

                Quest curQuest = new Quest(typeEnum, oHolder.getString("q_name"),
                        oHolder.getString("q_desc"), oHolder.getLong("q_long_time"),
                        oHolder.getInt("q_exp_value"));

                qHolder.add(curQuest);

            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return qHolder;
    }

    //data existance checking methods
    public boolean isCharacterSaved(){
        return mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).contains("c_player_name");
    }

    public boolean areQuestsSaved(){
        return mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).contains("quests");
    }


}
