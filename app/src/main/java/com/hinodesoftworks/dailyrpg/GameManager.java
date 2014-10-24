package com.hinodesoftworks.dailyrpg;

import android.content.Context;

import com.hinodesoftworks.dailyrpg.database.DBManager;

import java.util.ArrayList;

public class GameManager{
    private static GameManager _instance = null;
    private Context context;
    private DBManager dbManager;

    private DummyCharacter currChar = null;
    private DummyEnemy currEnemy = null;

    public ArrayList<String> hackyQList;

    static GameManager getInstance(Context context){
        if (_instance == null){
            _instance = new GameManager(context);
        }

        return _instance;
    }

    private GameManager(Context context){
        this.context = context;
        dbManager = DBManager.getInstance(context);

        hackyQList = new ArrayList<String>();
        hackyQList.add("Example Quest");
    }

    //battle functions
    public void newBattle(){
        if (doesCharacterExist()){
            //reset hp, if needed.
            currChar.currentHp = 100;
            //create new enemy
            currEnemy = new DummyEnemy();
        }
        else{
            throw new UnsupportedOperationException("No character");
        }
    }

    public boolean isPlayerDead(){
        return (currChar.currentHp <=0);
    }

    public boolean isEnemyDead(){
        return (currEnemy.currentHp <=0);
    }

    public void nextBattleTurn(int modeFlag){
        //player actions, depends on button pressed
        switch (modeFlag){
            case 0: //attack
                //enemy attack
                currChar.currentHp -= (currEnemy.attack - currChar.defense);

                //player attack//
                currEnemy.currentHp -= (currChar.attack - currEnemy.defense);
                break;
            case 1: //defend
                //enemy attack
                currChar.currentHp -= (currEnemy.attack - (currChar.defense + 5));

                break;
            case 2: //heal
                //enemy attack
                currChar.currentHp -= (currEnemy.attack - currChar.defense);

                //player heal
                currChar.currentHp += 30;
                break;

        }
    }

    //utility functions and accessors

    public boolean doesCharacterExist(){
        return currChar != null;
    }

    public void addCharacter(DummyCharacter toAdd){
        currChar = toAdd;
    }

    public String getCharacterName(){
        if (doesCharacterExist()){
            return currChar.name;
        }
        else{
            return "No Character";
        }
    }

    public String getCharacterClass(){
        if (doesCharacterExist()){
            return currChar.className;
        }
        else{
            return "No Character";
        }
    }

    public int getPlayerMaxHP(){
        if (doesCharacterExist()){
            return currChar.hp;
        }
        else{
            return 0;
        }
    }

    public int getPlayerCurrentHP(){
        if (doesCharacterExist()){
            return currChar.currentHp;
        }
        else{
            return 0;
        }
    }

    public int getPlayerAttack(){
        if (doesCharacterExist()){
            return currChar.attack;
        }
        else{
            return 0;
        }
    }

    public int getPlayerDefense(){
        if (doesCharacterExist()){
            return currChar.defense;
        }
        else{
            return 0;
        }
    }

    public String getEnemyName(){
        if (currEnemy != null){
            return currEnemy.name;
        }
        else{
            return "No Enemy";
        }
    }

    public int getEnemyMaxHP(){
        if (currEnemy != null){
            return currEnemy.hp;
        }
        else{
            return 0;
        }
    }

    public int getEnemyCurrentHP(){
        if (currEnemy != null){
            return currEnemy.currentHp;
        }
        else{
            return 0;
        }
    }

    public int getEnemyAttack(){
        if (currEnemy != null){
            return currEnemy.attack;
        }
        else{
            return 0;
        }
    }

    public int getEnemyDefense(){
        if (currEnemy != null){
            return currEnemy.defense;
        }
        else{
            return 0;
        }
    }

    //model classes
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
        protected int currentHp;

        protected DummyCharacter(String name, String className, int attack, int defense, int hp){
            this.name = name; this.className = className; this.attack = attack; this.defense = defense;
            this.hp = hp;
            this.currentHp = hp;
        }

        public void modifyHP(int value){
            currentHp+= value;
        }



    }

    private class DummyEnemy{
        protected String name = "Goblin";
        protected int hp = 40;
        protected int currentHp = 40;
        protected int attack = 30;
        protected int defense = 10;

        public void modifyHP(int value){
            currentHp += value;
        }
    }

}
