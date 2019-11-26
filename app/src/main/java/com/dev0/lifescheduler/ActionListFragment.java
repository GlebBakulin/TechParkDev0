package com.dev0.lifescheduler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActionListFragment extends Fragment {

    private ActionDataAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null)
            mAdapter.setSize(savedInstanceState.getInt("size"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_action, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.action_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ActionDataAdapter();
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton addActionButton = rootView.findViewById(R.id.action_add_action);
        addActionButton.setOnClickListener(v -> mAdapter.addAction());

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("size", mAdapter.getItemCount());
    }

    class ActionDataAdapter extends RecyclerView.Adapter<ActionViewHolder> {
        private ArrayList<Action> mActions;

        ActionDataAdapter() {
            mActions = new ArrayList<>();
        }

        void addAction() {
            mActions.add(new Action("Action name", "Action description"));
            notifyItemInserted(mActions.size());
        }

        void setSize(int size) {
            for (int i = mActions.size(); i < size; i++)
                addAction();
        }

        @NonNull
        @Override
        public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.action_list_item, parent, false);
            return new ActionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
            holder.mName.setText("Action name");  // Заглушка
            holder.mDescription.setText("Action description");
        }

        @Override
        public int getItemCount() {
            return mActions.size();
        }
    }

    class ActionViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName;
        private final TextView mDescription;

        ActionViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.action_list_item_name);
            mDescription = itemView.findViewById(R.id.action_list_item_description);
        }
    }
}
