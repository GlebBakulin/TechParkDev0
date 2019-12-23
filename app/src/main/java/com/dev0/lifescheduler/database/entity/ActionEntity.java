package com.dev0.lifescheduler.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ActionEntity {
    @PrimaryKey
    public int id;

    public String actionName;

    public String actionDesctription;

    public int startHour;

    public int endHour;

    public int startMin;

    public int endMin;

    public int startSec;

    public int endSec;
}
