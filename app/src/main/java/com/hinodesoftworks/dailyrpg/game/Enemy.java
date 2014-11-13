package com.hinodesoftworks.dailyrpg.game;

import android.util.Log;

public class Enemy {

    private String name;

    private int level;

    private int baseHP;
    private int baseAtk;
    private int baseDef;

    private int actualMaxHP;
    private int actualCurrentHP;

    //constructors
    public Enemy(String name, int level, int baseHP, int baseAtk, int baseDef) {
        this.name = name;
        this.level = level;
        this.baseHP = baseHP;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;

        this.actualMaxHP = this.actualCurrentHP = getModifiedHP();


        //TODO: Debug code, remove
        Log.e("Enemy Data Dump", "Name: " + this.name + " Level: " + this.level + " Base HP: "
                + this.baseHP + " ActualHP: " + this.actualMaxHP + " CurrentHP: " + this.actualCurrentHP
                + " Base Attack: " + this.baseAtk + " Base Defense: " + this.baseDef);
    }


    //hp manipulation
    public void modifyHP(int value) {
        this.actualCurrentHP += value;
        //can't be higher than max or lower than 0, modify if needed.
        if (this.actualCurrentHP > this.actualMaxHP) this.actualCurrentHP = this.actualMaxHP;
        if (this.actualCurrentHP < 0) this.actualCurrentHP = 0;
    }

    public boolean isDead() {
        return actualCurrentHP <= 0;
    }

    //actual statistic calculations
    public int getModifiedHP() {
        float modifier = 0.0f;

        //hp increases by 10% per level
        for (int h = 1; h < this.level; h++) {
            modifier += .1f;
        }

        float hpAdd = modifier * this.baseHP;

        //lazy truncate via casting
        int finalHp = this.baseHP + (int) hpAdd;

        return finalHp;
    }

    //TODO: Validate data in mutators
    //mutators/accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }

    public int getBaseAtk() {
        return baseAtk;
    }

    public void setBaseAtk(int baseAtk) {
        this.baseAtk = baseAtk;
    }

    public int getBaseDef() {
        return baseDef;
    }

    public void setBaseDef(int baseDef) {
        this.baseDef = baseDef;
    }

    public int getActualMaxHP() {
        return actualMaxHP;
    }

    public void setActualMaxHP(int actualMaxHP) {
        this.actualMaxHP = actualMaxHP;
    }

    public int getActualCurrentHP() {
        return actualCurrentHP;
    }

    public void setActualCurrentHP(int actualCurrentHP) {
        this.actualCurrentHP = actualCurrentHP;
    }
}
