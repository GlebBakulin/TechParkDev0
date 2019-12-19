package com.dev0.lifescheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "appdb.db";
    private static final String TASKS_TABLE_NAME = "tasks";
    private static final String TASKS_COL_ID = "id";
    private static final String TASKS_COL_NAME = "name";

    private static final String TASKS_COL_YEAR = "year";
    private static final String TASKS_COL_MONTH = "month";
    private static final String TASKS_COL_DAY = "day";
    private static final String TASKS_COL_HOUR = "hour";
    private static final String TASKS_COL_MINUTE = "minute";

    private static final String TASKS_COL_IS_STRICT = "is_strict";


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
                        + TASKS_COL_IS_STRICT + " INTEGER" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TASKS_TABLE_NAME);
        onCreate(db);
    }

    long insertTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        Calendar cal = task.getCalendar();
        cv.put(TASKS_COL_NAME, task.getName());
        int isStrict = task.isStrict() ? 1 : 0;
        cv.put(TASKS_COL_IS_STRICT, isStrict);

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

    public void updateTask(long id, String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TASKS_COL_NAME, name);
        db.update(TASKS_TABLE_NAME, cv, "id = ?", new String[] { Long.toString(id) });
    }

    long deleteTask(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TASKS_TABLE_NAME, "id = ?", new String[] { Long.toString(id) });
    }

     ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TASKS_TABLE_NAME, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(TASKS_COL_NAME));
                long id = cursor.getLong(cursor.getColumnIndex(TASKS_COL_ID));
                boolean isStrict = cursor.getInt(cursor.getColumnIndex(TASKS_COL_IS_STRICT)) == 1;

                int year = cursor.getInt(cursor.getColumnIndex(TASKS_COL_YEAR));
                int month = cursor.getInt(cursor.getColumnIndex(TASKS_COL_MONTH));
                int day = cursor.getInt(cursor.getColumnIndex(TASKS_COL_DAY));
                int hour = cursor.getInt(cursor.getColumnIndex(TASKS_COL_HOUR));
                int minute = cursor.getInt(cursor.getColumnIndex(TASKS_COL_MINUTE));

                Task task = new Task(name, id);
                task.setDate(year, month, day);
                task.setTime(hour, minute);

                task.setStrict(isStrict);
                taskList.add(task);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return taskList;
    }
}
