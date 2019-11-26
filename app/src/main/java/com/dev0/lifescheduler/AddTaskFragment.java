package com.dev0.lifescheduler;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

class AddTaskFragment extends Fragment {
    private AppCompatEditText editText;
    private AppCompatButton doneBtn;
    private Task mTask;
    private TaskEventListener mEventListener;
    private InputMethodManager mImm;

    static AddTaskFragment newInstance(TaskEventListener eventListener) {
        AddTaskFragment f = new AddTaskFragment();
        f.attachEventListener(eventListener);
        f.mTask = new Task();
        f.setRetainInstance(true);
        return f;
    }

    private void attachEventListener(TaskEventListener eventListener) {
        mEventListener = eventListener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        getActivity().findViewById(R.id.action_add_task).setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImm = (InputMethodManager)this.getContext()
                .getSystemService(getActivity().INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_add, container, false);

        doneBtn = rootView.findViewById(R.id.task_add_done);
        editText = rootView.findViewById(R.id.task_add_edit_text);

        doneBtn.setOnClickListener(v -> {
            mTask.setName(editText.getText().toString());

            mEventListener.handleEvent(mTask);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commit();
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doneBtn.performClick();
                return true;
            }
            return false;
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        mImm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (getActivity() != null) {
            if (getActivity().findViewById(R.id.bottom_navigation) != null)
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
            if (getActivity().findViewById(R.id.action_add_task) != null)
                getActivity().findViewById(R.id.action_add_task).setVisibility(View.VISIBLE);
        }
        mImm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}

