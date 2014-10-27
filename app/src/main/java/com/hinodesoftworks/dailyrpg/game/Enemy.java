package com.hinodesoftworks.dailyrpg.game;

public class Enemy{

    private String name;

    private int level;

    private int baseHP;
    private int baseAtk;
    private int baseDef;

    private int actualMaxHP;
    private int actualCurrentHP;

    //hp manipulation
    public void modifyHP(int value){
        
    }

    //TODO: Validate data in mutators
    //mutators/accessors
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getBaseHP(){
        return baseHP;
    }

    public void setBaseHP(int baseHP){
        this.baseHP = baseHP;
    }

    public int getBaseAtk(){
        return baseAtk;
    }

    public void setBaseAtk(int baseAtk){
        this.baseAtk = baseAtk;
    }

    public int getBaseDef(){
        return baseDef;
    }

    public void setBaseDef(int baseDef){
        this.baseDef = baseDef;
    }

    public int getActualMaxHP(){
        return actualMaxHP;
    }

    public void setActualMaxHP(int actualMaxHP){
        this.actualMaxHP = actualMaxHP;
    }

    public int getActualCurrentHP(){
        return actualCurrentHP;
    }

    public void setActualCurrentHP(int actualCurrentHP){
        this.actualCurrentHP = actualCurrentHP;
    }
}
