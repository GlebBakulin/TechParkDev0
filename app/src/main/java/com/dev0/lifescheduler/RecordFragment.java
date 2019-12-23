package com.dev0.lifescheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev0.lifescheduler.Models.AddTaskViewModel;

public class RecordFragment extends Fragment {
    RecordDataAdapter mAdapter;
    DBHelper mDBHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBHelper = new DBHelper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.records);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecordDataAdapter(mDBHelper.getAllTasks());
        recyclerView.setAdapter(mAdapter);
        AddTaskViewModel addTaskVM = ViewModelProviders.of(getActivity()).get(AddTaskViewModel.class);
        addTaskVM.setRecordDataAdapter(mAdapter);
        return rootView;
    }


}
