package com.hinodesoftworks.dailyrpg.game;

public class Character{
    //constants
    private static final int BASE_EXP_NEED = 1000;

    private String name;
    private String className;

    private int level;
    private int experience;

    private int baseHP;
    private int baseAtk;
    private int baseDef;

    private Item equipHead;
    private Item equipBody;
    private Item equipBoots;
    private Item equipWeapon;
    private Item equipShield;

    private int actualMaxHP;
    private int actualCurrentHP;

    //inventory

    //hp/exp manipulation
    public void modifyHP(int value){

    }

    public void modifyExp(int value){

    }

    public void reduceExpByTenPercent(){

    }


    //utility methods
    public String dumpToJSONString(){
        return "";
    }

    //TODO: validate input on mutators
    //mutators/accessors
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getClassName(){
        return className;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getExperience(){
        return experience;
    }

    public void setExperience(int experience){
        this.experience = experience;
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

    public Item getEquipHead(){
        return equipHead;
    }

    public void setEquipHead(Item equipHead){
        this.equipHead = equipHead;
    }

    public Item getEquipBody(){
        return equipBody;
    }

    public void setEquipBody(Item equipBody){
        this.equipBody = equipBody;
    }

    public Item getEquipBoots(){
        return equipBoots;
    }

    public void setEquipBoots(Item equipBoots){
        this.equipBoots = equipBoots;
    }

    public Item getEquipWeapon(){
        return equipWeapon;
    }

    public void setEquipWeapon(Item equipWeapon){
        this.equipWeapon = equipWeapon;
    }

    public Item getEquipShield(){
        return equipShield;
    }

    public void setEquipShield(Item equipShield){
        this.equipShield = equipShield;
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
