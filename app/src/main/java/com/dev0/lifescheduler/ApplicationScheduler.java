package com.dev0.lifescheduler;

import androidx.room.Room;

import com.dev0.lifescheduler.database.database.ActionDB;

public class ApplicationScheduler extends android.app.Application {
    public static ApplicationScheduler instance;

    private ActionDB actionDB;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        actionDB = Room.databaseBuilder(this, ActionDB.class, "ActionDB")
                .allowMainThreadQueries()
                .build();
    }

    public static ApplicationScheduler getInstance() {
        return instance;
    }

    public ActionDB getDatabase() {
        return actionDB;
    }
}
