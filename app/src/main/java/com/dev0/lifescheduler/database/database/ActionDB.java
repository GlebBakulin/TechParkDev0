package com.dev0.lifescheduler.database.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dev0.lifescheduler.ApplicationScheduler;
import com.dev0.lifescheduler.database.dao.ActionDao;
import com.dev0.lifescheduler.database.entity.ActionEntity;

@Database(entities = {ActionEntity.class}, version = 1)
public abstract class ActionDB extends RoomDatabase {
    public abstract ActionDao actionDao();

    public static ActionDB getDB() {
        return ApplicationScheduler.getInstance().getDatabase();
    }

}
