package com.dev0.lifescheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev0.lifescheduler.Models.AddTaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskListFragment extends Fragment {

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
        AddTaskViewModel addTaskVM = ViewModelProviders.of(getActivity()).get(AddTaskViewModel.class);
        addTaskVM.setAdapter(mAdapter);

        FloatingActionButton addTaskButton = rootView.findViewById(R.id.action_add_task);

        if (getActivity().getSupportFragmentManager().findFragmentByTag("add_task") != null)
            addTaskButton.hide();

        addTaskButton.setOnClickListener(v ->
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.task_add_container, new AddTaskFragment(), "add_task")
                    .commit()
        );

        return rootView;
    }

    public class TaskDataAdapter extends RecyclerView.Adapter<TaskViewHolder> {
        private ArrayList<Task> mTasks;

        public TaskDataAdapter() {
            mTasks = new ArrayList<>();
        }

        TaskDataAdapter(ArrayList<Task> tasks) {
            mTasks = tasks;
        }

        public boolean contains(Task task) {
            return mTasks.contains(task);
        }

        public void addTask(Task task) {
            mTasks.add(task);
            task.setId(mDbHelper.insertTask(task));
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
            holder.mDate.setText(task.getDate());
            holder.mTime.setText(task.getTime());
            holder.mTaskDoneBtn.setOnClickListener(v -> removeTask(position));
            holder.mTaskEditBtn.setOnClickListener(v -> {
                AddTaskViewModel vm = ViewModelProviders.of(getActivity()).get(AddTaskViewModel.class);
                vm.setTask(task);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.task_add_container, new AddTaskFragment(), null)
                        .commit();
            });
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName;
        private final TextView mDescription;
        private final TextView mTime;
        private final TextView mDate;
        private final AppCompatButton mTaskDoneBtn;
        private final AppCompatButton mTaskEditBtn;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.task_list_item_name);
            mDescription = null;
            mDate = itemView.findViewById(R.id.task_list_item_date);
            mTime = itemView.findViewById(R.id.task_list_item_time);
            mTaskDoneBtn = itemView.findViewById(R.id.task_done_btn);
            mTaskEditBtn = itemView.findViewById(R.id.task_edit_btn);
        }
    }
}
