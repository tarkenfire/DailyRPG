package com.hinodesoftworks.dailyrpg.game;

import android.app.Activity;

import java.util.Stack;

public class GameManager{
    public enum GameState{ STATE_NOT_INITIALIZED, STATE_READY, STATE_IN_BATTLE}
    public enum BattleMode{ MODE_RANDOM, MODE_DUNGEON, MODE_BOSS }

    private GameState currentState = GameState.STATE_NOT_INITIALIZED;
    private BattleMode currentMode = BattleMode.MODE_RANDOM;
    private Character playerCharacter = null;
    private Enemy currentEnemy = null;
    private Stack<Enemy> enemyStack;
    private int rewardCounter = 0;

    private static GameManager _instance = null;
    private GameListener _listener = null;

    public static GameManager getInstance(){
        if (_instance == null){
            _instance = new GameManager();
        }
        return _instance;
    }

    public void init(Character playerCharacter, GameListener listener){
        //if already initialized, don't do anything
        if (currentState == GameState.STATE_READY || currentState == GameState.STATE_IN_BATTLE){
            return;
        }

        this.playerCharacter = playerCharacter;
        _listener = listener;
        currentState = GameState.STATE_READY;
    }

    //battle mechanics section
    //todo: use derived statistics instead of raw ones

    public void setBattleMode(BattleMode mode){
        this.currentMode = mode;
    }

    public void attack(){
        playerCharacter.modifyHP(-(currentEnemy.getBaseAtk() > playerCharacter.getBaseDef() ?
                (currentEnemy.getBaseAtk() - playerCharacter.getBaseDef())  : 0));

        currentEnemy.modifyHP(-(playerCharacter.getBaseAtk() > currentEnemy.getBaseDef() ?
                (playerCharacter.getBaseAtk() - currentEnemy.getBaseDef())  : 0));
    }

    public void defend(){
        playerCharacter.modifyHP(-(currentEnemy.getBaseAtk() > playerCharacter.getBaseDef() ?
                (currentEnemy.getBaseAtk() - playerCharacter.getBaseDef()) + 10  : 0));
    }

    public void useItem(Item itemToUse){
        //TODO: implement
    }

    public void flee(){
        _listener.onBattleFled();

        //cleanup since singleton will persist.
        enemyStack.clear();
        currentEnemy = null;
        playerCharacter.restoreHPToFull();
        currentState = GameState.STATE_READY;
    }

    private void nextTurn(){
        //check for victory conditions
        if (currentEnemy.isDead() || playerCharacter.isDead()){



        }

        //fire callback to UI
        _listener.onTurnEnd(playerCharacter, currentEnemy);
    }

    private void battleEndVictory(){
        if (!enemyStack.empty()){
            currentEnemy = enemyStack.pop();
            playerCharacter.restoreHPToFull();
            nextTurn();
            return;
        }

        currentState = GameState.STATE_READY;
    }

    private void battleEndDefeat(){

    }

    //TODO: pull enemies from data source
    public void newRandomBattle(){
        //populate stack, even for one enemy
        enemyStack = new Stack<Enemy>();
        enemyStack.push(new Enemy("Enemy", 1, 50, 10, 5));
        currentEnemy = enemyStack.pop();

        nextTurn();

        currentState = GameState.STATE_IN_BATTLE;
    }

    public void newDungeonBattle(){}
    public void newBossRushBattle(){}

    //game utilities


    public GameState getCurrentState(){
        return currentState;
    }

    ///listener interface
    public interface GameListener{
        //random battle
        public void onBattleEnd();
        public void onTurnEnd(Character character, Enemy enemy);
        public void onBattleFled();


    }


}
