package com.dev0.lifescheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "appdb.db";
    private static final String TASKS_TABLE_NAME = "tasks";
    private static final String TASKS_COL_ID = "id";
    private static final String TASKS_COL_NAME = "name";


    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE  " + TASKS_TABLE_NAME +
                "(" + TASKS_COL_ID + " INTEGER PRIMARY KEY, "
                        + TASKS_COL_NAME + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TASKS_TABLE_NAME);
        onCreate(db);
    }

    long insertTask(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASKS_COL_NAME, name);
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

                taskList.add(new Task(name, id));

                cursor.moveToNext();
            }
            cursor.close();
        }

        return taskList;
    }
}
