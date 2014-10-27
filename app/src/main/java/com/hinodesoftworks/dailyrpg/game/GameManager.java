package com.hinodesoftworks.dailyrpg.game;

import android.app.Activity;

import java.util.Stack;

public class GameManager{
    public enum GameState{ STATE_NOT_INITIALIZED, STATE_READY, STATE_IN_BATTLE}

    private GameState currentState = GameState.STATE_NOT_INITIALIZED;
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
        this.playerCharacter = playerCharacter;
        _listener = listener;
        currentState = GameState.STATE_READY;
    }

    //battle mechanics section
    public void attack(){}
    public void defend(){}
    public void useItem(Item itemToUse){}
    public void flee(){}

    private void nextTurn(){}

    private void battleEnd(){}

    private void nextBattle(Enemy nextEnemy){}

    public void newRandomBattle(){}
    public void newDungeonBattle(){}
    public void newBossRushBattle(){}

    //game utilities



    ///listener interface
    public interface GameListener{
        //random battle
        public void onBattleStart();
        public void onBattleEnd();
        public void onTurnEnd();

    }

}
