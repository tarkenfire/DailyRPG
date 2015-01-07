package com.hinodesoftworks.dailyrpg.game;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GameManager {
    public enum GameState {STATE_NOT_INITIALIZED, STATE_READY, STATE_IN_BATTLE}

    public enum BattleMode {MODE_RANDOM, MODE_DUNGEON, MODE_BOSS}

    public static final int BATTLE_CHOICE_ATTACK = 101;
    public static final int BATTLE_CHOICE_DEFEND = 102;
    public static final int BATTLE_CHOICE_FLEE = 104;

    private Bitmap sUserImage = null;

    private GameState currentState = GameState.STATE_NOT_INITIALIZED;
    private BattleMode currentMode = BattleMode.MODE_RANDOM;

    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    public int getScore(){ return score; }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }
    public void setCurrentEnemy(int pos){
        if (enemyList != null){
            currentEnemy = enemyList.get(pos);
        }
    }

    private Character playerCharacter = null;
    private Enemy currentEnemy = null;
    private ArrayList<Enemy> enemyList;
    private int score;

    private static GameManager _instance = null;
    private GameListener _listener = null;

    public static GameManager getInstance() {
        if (_instance == null) {
            _instance = new GameManager();
        }
        return _instance;
    }

    public void init(Character playerCharacter, GameListener listener) {
        //if already initialized, don't do anything
        if (currentState == GameState.STATE_READY || currentState == GameState.STATE_IN_BATTLE) {
            return;
        }

        this.playerCharacter = playerCharacter;
        _listener = listener;
        currentState = GameState.STATE_READY;
    }

    //battle mechanics section
    //todo: use derived statistics instead of raw ones

    public void attack() {
        playerCharacter.modifyHP(-(currentEnemy.getBaseAtk() > playerCharacter.getBaseDef() ?
                (currentEnemy.getBaseAtk() - playerCharacter.getBaseDef()) : 0));

        currentEnemy.modifyHP(-(playerCharacter.getBaseAtk() > currentEnemy.getBaseDef() ?
                (playerCharacter.getBaseAtk() - currentEnemy.getBaseDef()) : 0));

        nextTurn();
    }

    public void defend() {
        playerCharacter.modifyHP(-(currentEnemy.getBaseAtk() > playerCharacter.getBaseDef() ?
                (currentEnemy.getBaseAtk() - playerCharacter.getBaseDef()) + 10 : 0));
        nextTurn();
    }


    public void flee() {
        _listener.onBattleFled();

        //cleanup since singleton will persist.
        currentEnemy = null;
        playerCharacter.restoreHPToFull();
        currentState = GameState.STATE_READY;
    }

    private void nextTurn() {
        //check for victory conditions
        if (currentEnemy.isDead()) {
            battleEndVictory();
            return;
        }

        if (playerCharacter.isDead()) {
            battleEndDefeat();
            return;
        }

        //fire callback to UI
        _listener.onTurnEnd(playerCharacter, currentEnemy);
    }

    private void battleEndVictory() {
        score = currentEnemy.getLevel();

        _listener.onBattleEnd();
        currentState = GameState.STATE_READY;

        //todo: duplicate code
        //cleanup
        playerCharacter.restoreHPToFull();
        currentEnemy.modifyHP(currentEnemy.getActualMaxHP());
    }

    private void battleEndDefeat() {
        _listener.onBattleEnd();
        currentState = GameState.STATE_READY;

        //cleanup
        playerCharacter.restoreHPToFull();
        currentEnemy.modifyHP(currentEnemy.getActualMaxHP());
    }

    //battle creation
    public void generateEnemies(int playerLevel){
        enemyList = new ArrayList<Enemy>();

        //gen 5 enemies equal and above the player level
        int levelHolder = playerLevel;

        //get random name
        String[] names = {"Goblin", "Drake", "Bandit", "Muffin Demon", "Naga"};



        for (int i = 0; i < 5; i++){
            enemyList.add(new Enemy(names[new Random().nextInt(names.length)], levelHolder,
                    50 + levelHolder * 4 , 40 + levelHolder, 30 + levelHolder));
            levelHolder++;
        }
    }

    public ArrayList<Enemy> getEnemyList(){
        return enemyList;
    }

    //game utilities
    public void updateCharacter(Character character) {
        this.playerCharacter = character;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void nullEnemyList(){
        this.enemyList = null;
    }

    ///listener interface
    public interface GameListener {
        //random battle
        public void onBattleEnd();

        public void onTurnEnd(Character character, Enemy enemy);

        public void onBattleFled();
    }

    public Bitmap getsUserImage() {
        return sUserImage;
    }

    public void setsUserImage(Bitmap sUserImage) {
        this.sUserImage = sUserImage;
    }
}
