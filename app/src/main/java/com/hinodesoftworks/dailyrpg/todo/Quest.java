package com.hinodesoftworks.dailyrpg.todo;

public class Quest {
    public enum QuestType {QUEST_DAILY, QUEST_MONTHLY, QUEST_SINGLE}

    //quest fields
    private QuestType currentType = QuestType.QUEST_SINGLE;
    private String questName, questDetails;
    private long dueTimeInMillis;
    private int experiencePoints;
    private boolean isOverdue = false;


    public Quest(QuestType questType, String questName, String questDetails, long dueTimeInMillis,
                    int rewardPoints) {
        this.currentType = questType; this.questName = questName; this.questDetails = questDetails;
        this.dueTimeInMillis = dueTimeInMillis; this.experiencePoints = rewardPoints;
    }

    public void setExperiencePoints(int exp){
        experiencePoints = exp;
    }

    public int getExpValue(){
        return experiencePoints;
    }

    public QuestType getCurrentType() {
        return currentType;
    }

    public void setCurrentType(QuestType currentType) {
        this.currentType = currentType;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getQuestDetails() {
        return questDetails;
    }

    public void setQuestDetails(String questDetails) {
        this.questDetails = questDetails;
    }

    public long getDueTimeInMillis() {
        return dueTimeInMillis;
    }

    public void setDueTimeInMillis(long dueTimeInMillis) {
        this.dueTimeInMillis = dueTimeInMillis;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean isOverdue) {
        this.isOverdue = isOverdue;
    }
}
