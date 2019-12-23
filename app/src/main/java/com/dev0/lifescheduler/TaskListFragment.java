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
        private ArrayList<LifeTask> mLifeTasks;

        public TaskDataAdapter() {
            mLifeTasks = new ArrayList<>();
        }

        TaskDataAdapter(ArrayList<LifeTask> lifeTasks) {
            mLifeTasks = new ArrayList<>();
            for (LifeTask i : lifeTasks)
                if (!i.isComplete())
                    mLifeTasks.add(i);
        }

        public boolean contains(LifeTask lifeTask) {
            return mLifeTasks.contains(lifeTask);
        }

        public void addTask(LifeTask lifeTask) {
            mLifeTasks.add(lifeTask);
            lifeTask.setId(mDbHelper.insertTask(lifeTask));
            notifyDataSetChanged();
            notifyItemInserted(mLifeTasks.size());
        }

        void completeTask(int pos) {
            mLifeTasks.get(pos).setCompletion(true);
            mDbHelper.updateTask(mLifeTasks.get(pos).getId(), DBHelper.TASKS_COL_IS_DONE, 1);
            mLifeTasks.remove(pos);
            notifyItemRemoved(pos);
        }
        void removeTask(int pos) {
            mDbHelper.deleteTask(mLifeTasks.get(pos).getId());
            mLifeTasks.remove(pos);
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
            LifeTask lifeTask = mLifeTasks.get(position);
            holder.mName.setText(lifeTask.getName());
            holder.mDate.setText(lifeTask.getDate());
            holder.mTime.setText(lifeTask.getTime());
            holder.mTaskDoneBtn.setOnClickListener(v -> completeTask(position));
            holder.mTaskEditBtn.setOnClickListener(v -> {
                AddTaskViewModel vm = ViewModelProviders.of(getActivity()).get(AddTaskViewModel.class);
                vm.setTask(lifeTask);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.task_add_container, new AddTaskFragment(), null)
                        .commit();
            });
        }

        @Override
        public int getItemCount() {
            return mLifeTasks.size();
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
