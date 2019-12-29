package com.dev0.lifescheduler.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ActionEntity {
    @PrimaryKey (autoGenerate = true)
    public long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDesctription() {
        return actionDesctription;
    }

    public void setActionDesctription(String actionDesctription) {
        this.actionDesctription = actionDesctription;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public int getStartSec() {
        return startSec;
    }

    public void setStartSec(int startSec) {
        this.startSec = startSec;
    }

    public int getEndSec() {
        return endSec;
    }

    public void setEndSec(int endSec) {
        this.endSec = endSec;
    }

    public String actionName;

    public String actionDesctription;

    public int startHour;

    public int endHour;

    public int startMin;

    public int endMin;

    public int startSec;

    public int endSec;
}
