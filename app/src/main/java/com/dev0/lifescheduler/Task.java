package com.dev0.lifescheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Task {
    private String mName;
    private long mId;
    private boolean mIsStrict = false;
    private Calendar mDateTime = Calendar.getInstance();

    Task() {
        mName = "";
    }

    Task(String name) {
        mName = name;
    }

    Task(String name, long id) {
        mName = name;
        mId = id;
    }

    void setName(String name) {
        mName = name;
    }

    String getName() {
        return mName;
    }

    void setId(long id) {
        mId = id;
    }

    long getId() {
        return mId;
    }

    void setDate(int year, int month, int monthOfDay) {
        mDateTime.set(year, month, monthOfDay);
    }

    String getDate() {
        return SimpleDateFormat.getDateInstance().format(mDateTime.getTime());
    }

    void setDate(Calendar date) {
        mDateTime = date;
    }

    Calendar getCalendar() {
        return mDateTime;
    }

    void setTime(int hourOfDay, int minute) {
        mDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mDateTime.set(Calendar.MINUTE, minute);
    }

    String getTime() {
        return SimpleDateFormat.getTimeInstance().format(mDateTime.getTime());
    }

    boolean isStrict() {
        return mIsStrict;
    }

    void setStrict(boolean isStrict) {
        mIsStrict = isStrict;
    }
}