package com.hinodesoftworks.dailyrpg.game;

import java.util.Map;

public class Character {
    //constants
    private static final int BASE_EXP_NEED = 1000;

    private String name;
    private String className;

    private int level;
    private int experience;
    private int gold;

    private int baseHP;
    private int baseAtk;
    private int baseDef;

    private Item equipHead = null;
    private Item equipBody = null;
    private Item equipBoots = null;
    private Item equipWeapon = null;
    private Item equipShield = null;

    private int actualMaxHP;
    private int actualCurrentHP;
    private int actualAtk;
    private int actualDef;

    private boolean hasEquipment = false;

    //constructors
    public Character(String name, String className, int level, int baseHP,
                     int baseAtk, int baseDef, Map<String, Equipment> equipment) {
        this.name = name;
        this.className = className;
        this.level = level;
        this.baseHP = baseHP;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;

        //derive hp
        this.actualMaxHP = this.actualCurrentHP = getModifiedHP();

        setEquipedItems(equipment);
    }

    //factory method for "new", blank character creation
    public static Character createNewCharacter(String name, String className) {
        //TODO: Make different classes have different stats
        return new Character(name, className, 1, 100, 40, 30, null);
    }

    //inventory
    private void setEquipedItems(Map<String, Equipment> itemMap) {
        if (itemMap == null) return;

    }

    //hp/exp manipulation
    public void modifyHP(int value) {
        this.actualCurrentHP += value;
        //can't be higher than max or lower than 0, modify if needed.
        if (this.actualCurrentHP > this.actualMaxHP) this.actualCurrentHP = this.actualMaxHP;
        if (this.actualCurrentHP < 0) this.actualCurrentHP = 0;
    }

    public void modifyExp(int value) {
        actualCurrentHP += value;
    }

    public void reduceExpByTenPercent() {
        experience -= experience * .1f;
    }

    public void restoreHPToFull() {
        actualCurrentHP = actualMaxHP;
    }

    public boolean isDead() {
        return actualCurrentHP <= 0;
    }

    //stat related methods
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


    //utility methods
    public String dumpToJSONString() {
        return "";
    }


    //TODO: validate input on mutators
    //mutators/accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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

    public int getActualAtk() {
        return actualAtk;
    }

    public void setActualAtk(int actualAtk) {
        this.actualAtk = actualAtk;
    }

    public int getActualDef() {
        return actualDef;
    }

    public void setActualDef(int actualDef) {
        this.actualDef = actualDef;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
