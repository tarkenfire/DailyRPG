package com.hinodesoftworks.dailyrpg;

public class GameManager{
    private static GameManager _instance = null;

    static GameManager getInstance(){
        if (_instance == null){
            _instance = new GameManager();
        }

        return _instance;
    }

    private GameManager(){}

    public void startManager(){

    }

}
