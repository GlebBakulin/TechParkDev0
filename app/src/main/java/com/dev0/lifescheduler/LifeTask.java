package com.dev0.lifescheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LifeTask {
    private String mName;
    private long mId;
    private boolean mIsStrict = false;
    private boolean mIsComplete = false;
    private Calendar mDateTime = Calendar.getInstance();

    LifeTask() {
        mName = "";
    }

    LifeTask(String name) {
        mName = name;
    }

    LifeTask(String name, long id) {
        mName = name;
        mId = id;
        mDateTime.setTimeInMillis(0);
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

    public String getDate() {
        return SimpleDateFormat.getDateInstance().format(mDateTime.getTime());
    }

    public void setDate(Calendar date) {
        mDateTime = date;
    }

    public Calendar getCalendar() {
        return mDateTime;
    }

    public void setTime(int hourOfDay, int minute) {
        mDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mDateTime.set(Calendar.MINUTE, minute);
    }

    public String getTime() {
        return SimpleDateFormat.getTimeInstance().format(mDateTime.getTime());
    }

    public boolean isStrict() {
        return mIsStrict;
    }

    public void setStrict(boolean isStrict) {
        mIsStrict = isStrict;
    }

    public boolean isComplete() {
        return mIsComplete;
    }

    public void setCompletion(boolean isComplete) {
        mIsComplete = isComplete;
    }

    public boolean isExpired() {
        if (mIsStrict && !mIsComplete) {
            if (Calendar.getInstance().compareTo(mDateTime) > 0)
                return true;
        }
        return false;
    }
}