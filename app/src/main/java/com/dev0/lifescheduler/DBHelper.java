package com.dev0.lifescheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class DBHelper extends SQLiteOpenHelper {
    private boolean mIsTaskUpdated = false;

    static final String DATABASE_NAME = "appdb.db";
    static final String TASKS_TABLE_NAME = "tasks";
    static final String TASKS_COL_ID = "id";
    static final String TASKS_COL_NAME = "name";

    static final String TASKS_COL_YEAR = "year";
    static final String TASKS_COL_MONTH = "month";
    static final String TASKS_COL_DAY = "day";
    static final String TASKS_COL_HOUR = "hour";
    static final String TASKS_COL_MINUTE = "minute";

    static final String TASKS_COL_IS_STRICT = "is_strict";
    static final String TASKS_COL_IS_DONE = "is_done";


    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE  " + TASKS_TABLE_NAME +
                "(" + TASKS_COL_ID + " INTEGER PRIMARY KEY, "
                        + TASKS_COL_NAME + " TEXT, "
                        + TASKS_COL_YEAR + " INTEGER, "
                        + TASKS_COL_MONTH + " INTEGER, "
                        + TASKS_COL_DAY + " INTEGER, "
                        + TASKS_COL_HOUR + " INTEGER, "
                        + TASKS_COL_MINUTE + " INTEGER, "
                        + TASKS_COL_IS_STRICT + " INTEGER, "
                        + TASKS_COL_IS_DONE + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TASKS_TABLE_NAME);
        onCreate(db);
    }

    long insertTask(LifeTask lifeTask) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        Calendar cal = lifeTask.getCalendar();
        cv.put(TASKS_COL_NAME, lifeTask.getName());

        int isStrict = lifeTask.isStrict() ? 1 : 0;
        cv.put(TASKS_COL_IS_STRICT, isStrict);

        int isDone = lifeTask.isComplete() ? 1 : 0;
        cv.put(TASKS_COL_IS_STRICT, isDone);

        cv.put(TASKS_COL_YEAR, cal.get(Calendar.YEAR));
        cv.put(TASKS_COL_MONTH, cal.get(Calendar.MONTH));
        cv.put(TASKS_COL_DAY, cal.get(Calendar.DAY_OF_MONTH));
        cv.put(TASKS_COL_HOUR, cal.get(Calendar.HOUR));
        cv.put(TASKS_COL_MINUTE, cal.get(Calendar.MINUTE));

        return db.insert(TASKS_TABLE_NAME, null, cv);
    }

    public Cursor getData(long id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from tasks where id="+id+"", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TASKS_TABLE_NAME);
    }

    public void updateTask(long id, String col, Object val) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (val instanceof Integer)
            cv.put(col, (Integer) val);
        else if (val instanceof String)
            cv.put(col, (String) val);
        db.update(TASKS_TABLE_NAME, cv, "id = ?", new String[] { Long.toString(id) });
        mIsTaskUpdated = true;
    }

    public long deleteTask(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TASKS_TABLE_NAME, "id = ?", new String[] { Long.toString(id) });
    }

    public ArrayList<LifeTask> getAllTasks() {
        ArrayList<LifeTask> lifeTaskList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TASKS_TABLE_NAME, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(TASKS_COL_NAME));
                long id = cursor.getLong(cursor.getColumnIndex(TASKS_COL_ID));
                boolean isStrict = cursor.getInt(cursor.getColumnIndex(TASKS_COL_IS_STRICT)) == 1;
                boolean isDone = cursor.getInt(cursor.getColumnIndex(TASKS_COL_IS_DONE)) == 1;

                int year = cursor.getInt(cursor.getColumnIndex(TASKS_COL_YEAR));
                int month = cursor.getInt(cursor.getColumnIndex(TASKS_COL_MONTH));
                int day = cursor.getInt(cursor.getColumnIndex(TASKS_COL_DAY));
                int hour = cursor.getInt(cursor.getColumnIndex(TASKS_COL_HOUR));
                int minute = cursor.getInt(cursor.getColumnIndex(TASKS_COL_MINUTE));

                LifeTask lifeTask = new LifeTask(name, id);
                lifeTask.setDate(year, month, day);
                lifeTask.setTime(hour, minute);

                lifeTask.setStrict(isStrict);
                lifeTask.setCompletion(isDone);
                lifeTaskList.add(lifeTask);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return lifeTaskList;
    }

    public boolean isTaskUpdated() {
        return mIsTaskUpdated;
    }
}
