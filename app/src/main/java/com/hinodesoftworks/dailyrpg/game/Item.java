package com.hinodesoftworks.dailyrpg.game;

public class Item {
    private int itemValue;
    private String itemName;
    private String drawableResourcePath;

    //constructors


    //accessors/mutators
    public int getItemValue() {
        return itemValue;
    }

    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDrawableResourcePath() {
        return drawableResourcePath;
    }

    public void setDrawableResourcePath(String drawableResourcePath) {
        this.drawableResourcePath = drawableResourcePath;
    }
}
