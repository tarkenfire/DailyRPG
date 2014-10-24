package com.hinodesoftworks.dailyrpg;

import android.content.Context;

import com.hinodesoftworks.dailyrpg.database.DBManager;

public class GameManager{
    private static GameManager _instance = null;
    private Context context;
    private DBManager dbManager;

    private DummyCharacter currChar = null;

    static GameManager getInstance(Context context){
        if (_instance == null){
            _instance = new GameManager(context);
        }

        return _instance;
    }

    private GameManager(Context context){
        this.context = context;
        dbManager = DBManager.getInstance(context);
    }

    public void startManager(){

    }

    public boolean doesCharacterExist(){
        return currChar != null;
    }

    public void addCharacter(DummyCharacter toAdd){
        currChar = toAdd;
    }

    public DummyCharacter createDummyCharacter(String name, String className){
        DummyCharacter holder = new DummyCharacter(name, className, 20, 20, 100);
        return holder;
    }



    private class DummyCharacter{
        protected String name;
        protected String className;
        protected int attack;
        protected int defense;
        protected int hp;

        protected DummyCharacter(String name, String className, int attack, int defense, int hp){
            this.name = name; this.className = className; this.attack = attack; this.defense = defense;
            this.hp = hp;
        }

    }

    private class DummyEnemy{

    }

}
