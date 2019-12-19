package com.dev0.lifescheduler.Models;

import androidx.lifecycle.ViewModel;

import com.dev0.lifescheduler.Task;
import com.dev0.lifescheduler.TaskListFragment;

public class AddTaskViewModel extends ViewModel {
    private TaskListFragment.TaskDataAdapter mAdapter;
    private Task mTask = null;

    public void setAdapter(TaskListFragment.TaskDataAdapter adapter) {
        mAdapter = adapter;
    }

    public void addTaskToList() {
        if (!mAdapter.contains(mTask))
            mAdapter.addTask(mTask);
        mAdapter.notifyDataSetChanged();
    }

    public Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        mTask = task;
    }
}
