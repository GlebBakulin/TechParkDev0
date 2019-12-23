package com.dev0.lifescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class RecordDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int TYPE_DATE = 0;
    final int TYPE_TASK = 1;
    private ArrayList<Object> mObjects;

    public RecordDataAdapter(ArrayList<LifeTask> tasks) {
        mObjects = new ArrayList<>();
        setData(tasks);

    }

    public void setData(ArrayList<LifeTask> tasks) {
        mObjects.clear();
        tasks.stream()
                .filter(t -> t.isComplete())
                .sorted(Comparator.comparing(LifeTask::getCalendar).reversed())
                .collect(Collectors.groupingBy(LifeTask::getDate))
                .forEach((key, val) -> {
                    mObjects.add(key);
                    mObjects.addAll(val);
                });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_DATE:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.date_list_item, parent, false);
                return new DateViewHolder(view);
            case TYPE_TASK:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.completed_task_list_item, parent, false);
                return new TaskViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mObjects.get(position) instanceof LifeTask)
            return TYPE_TASK;
        return TYPE_DATE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_TASK:
                TaskViewHolder tvh = (TaskViewHolder) holder;
                LifeTask task = (LifeTask) mObjects.get(position);
                tvh.mTask = task;
                tvh.updateView();
                break;
            case TYPE_DATE:
                DateViewHolder dvh = (DateViewHolder) holder;
                String date = (String) mObjects.get(position);
                dvh.mDate.setText(date);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mDate;
        TextView mTime;
        TextView mType;

        LifeTask mTask = null;

        public TaskViewHolder(@NonNull View view) {
            super(view);
            mName = view.findViewById(R.id.completed_task_list_item_name);
            mDate = view.findViewById(R.id.completed_task_list_item_date);
            mTime = view.findViewById(R.id.completed_task_list_item_time);
            mType = view.findViewById(R.id.completed_task_list_item_type);
        }

        public void updateView() {
            mName.setText(mTask.getName());
            mDate.setText(mTask.getDate());
            mTime.setText(mTask.getTime());
            setType(mTask.isExpired());
        }

        public void setType(boolean isExpired) {
            if (isExpired)
                mType.setText("EXPIRED");
            else
                mType.setText("");
        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        TextView mDate;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.date_list_item_date);
        }
    }

}