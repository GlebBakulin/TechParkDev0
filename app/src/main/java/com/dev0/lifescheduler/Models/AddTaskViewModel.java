package com.dev0.lifescheduler.Models;

import androidx.lifecycle.ViewModel;

import com.dev0.lifescheduler.DBHelper;
import com.dev0.lifescheduler.LifeTask;
import com.dev0.lifescheduler.RecordDataAdapter;
import com.dev0.lifescheduler.TaskListFragment;

public class AddTaskViewModel extends ViewModel {
    private TaskListFragment.TaskDataAdapter taskDataAdapter;
    private RecordDataAdapter recordDataAdapter = null;
    private LifeTask mLifeTask = null;
    private DBHelper mDBHelper = null;

    public void setTaskListAdapter(TaskListFragment.TaskDataAdapter adapter) {
        taskDataAdapter = adapter;
    }

    public void setRecordDataAdapter(RecordDataAdapter adapter) {
        recordDataAdapter = adapter;
    }

    public void setDBHelper(DBHelper helper) {
        mDBHelper = helper;
    }

    public void addTaskToList() {
        if (!taskDataAdapter.contains(mLifeTask))
            taskDataAdapter.addTask(mLifeTask);
        taskDataAdapter.notifyDataSetChanged();
    }

    public LifeTask getTask() {
        return mLifeTask;
    }

    public void setTask(LifeTask lifeTask) {
        mLifeTask = lifeTask;
    }

    public void updateTask() {
        if (recordDataAdapter != null && mDBHelper != null)
            recordDataAdapter.setData(mDBHelper.getAllTasks());
    }
}
