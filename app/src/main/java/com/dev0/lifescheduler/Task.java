package com.dev0.lifescheduler;

class Task {
    private String mName;
    private long mId;

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
}