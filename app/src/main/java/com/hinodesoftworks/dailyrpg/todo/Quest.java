package com.hinodesoftworks.dailyrpg.todo;

public class Quest{
    public enum QuestType{QUEST_DAILY, QUEST_MONTHLY, QUEST_SINGLE}

    //quest fields
    private QuestType currentType = QuestType.QUEST_SINGLE;
    private String questName, questDetails;


    public Quest(QuestType questType, String questName){

    }
}
