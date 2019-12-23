package com.dev0.lifescheduler.Models;

import androidx.lifecycle.ViewModel;

import com.dev0.lifescheduler.LifeTask;
import com.dev0.lifescheduler.TaskListFragment;

public class AddTaskViewModel extends ViewModel {
    private TaskListFragment.TaskDataAdapter mAdapter;
    private LifeTask mLifeTask = null;

    public void setAdapter(TaskListFragment.TaskDataAdapter adapter) {
        mAdapter = adapter;
    }

    public void addTaskToList() {
        if (!mAdapter.contains(mLifeTask))
            mAdapter.addTask(mLifeTask);
        mAdapter.notifyDataSetChanged();
    }

    public LifeTask getTask() {
        return mLifeTask;
    }

    public void setTask(LifeTask lifeTask) {
        mLifeTask = lifeTask;
    }
}
