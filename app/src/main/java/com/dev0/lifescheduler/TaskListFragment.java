package com.dev0.lifescheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

interface TaskEventListener {
    void handleEvent(Task task);
}

public class TaskListFragment extends Fragment implements TaskEventListener {

    private TaskDataAdapter mAdapter;
    private DBHelper mDbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        mDbHelper = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.task_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new TaskDataAdapter(mDbHelper.getAllTasks());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton addTaskButton = rootView.findViewById(R.id.action_add_task);

        if (getActivity().getSupportFragmentManager().findFragmentByTag("add_task") != null)
            addTaskButton.hide();

        addTaskButton.setOnClickListener(v ->
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.task_add_container, AddTaskFragment.newInstance(this), "add_task")
                    .commit()
        );

        return rootView;
    }

    @Override
    public void handleEvent(Task task) {
        mAdapter.addTask(task);
    }

    class TaskDataAdapter extends RecyclerView.Adapter<TaskViewHolder> {
        private ArrayList<Task> mTasks;

        public TaskDataAdapter() {
            mTasks = new ArrayList<>();
        }

        TaskDataAdapter(ArrayList<Task> tasks) {
            mTasks = tasks;
        }

        void addTask(Task task) {
            mTasks.add(task);
            task.setId(mDbHelper.insertTask(task.getName()));
            notifyDataSetChanged();
            notifyItemInserted(mTasks.size());
        }

        void removeTask(int pos) {
            mDbHelper.deleteTask(mTasks.get(pos).getId());
            mTasks.remove(pos);
            notifyDataSetChanged();
            notifyItemRemoved(pos);
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.task_list_item, parent, false);
            return new TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.mName.setText(task.getName());
            holder.mTaskDone.setOnClickListener(v -> removeTask(position));
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName;
        private final TextView mDescription;
        private final AppCompatButton mTaskDone;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.task_list_item_name);
            mDescription = null;
            mTaskDone = itemView.findViewById(R.id.task_done_btn);
        }
    }
}
