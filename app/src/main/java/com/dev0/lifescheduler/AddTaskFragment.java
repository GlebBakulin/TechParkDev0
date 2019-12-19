package com.dev0.lifescheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dev0.lifescheduler.Models.AddTaskViewModel;

import java.util.Calendar;

public class AddTaskFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance();
    private AppCompatEditText editText;
    private AppCompatButton doneBtn;
    private AppCompatButton dateBtn;
    private AppCompatButton timeBtn;
    private AppCompatButton typeBtn;
    private Task mTask;
    private InputMethodManager mImm;
    private AddTaskViewModel mAddTaskModel;

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

    private DatePickerDialog.OnDateSetListener datePickerListener = (DatePicker view, int year, int month, int dayOfMonth) -> {
        mTask.setDate(year, month, dayOfMonth);
    };
    private TimePickerDialog.OnTimeSetListener timePickerListener = (TimePicker view, int hourOfDay, int minute) -> {
        mTask.setTime(hourOfDay, minute);
    };

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_add, container, false);

        doneBtn = rootView.findViewById(R.id.task_add_done_btn);
        dateBtn = rootView.findViewById(R.id.task_add_date_btn);
        timeBtn = rootView.findViewById(R.id.task_add_time_btn);
        typeBtn = rootView.findViewById(R.id.task_add_type_btn);
        editText = rootView.findViewById(R.id.task_add_edit_text);

        mAddTaskModel = ViewModelProviders.of(getActivity()).get(AddTaskViewModel.class);
        mTask = mAddTaskModel.getTask();
        if (mTask != null) {
            editText.setText(mTask.getName());
        } else {
            mTask = new Task();
            mAddTaskModel.setTask(mTask);
        }

        doneBtn.setOnClickListener(v -> {
            mTask.setName(editText.getText().toString());

            mAddTaskModel.addTaskToList();
            mAddTaskModel.setTask(null);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commit();
        });

        dateBtn.setOnClickListener(v -> {
            Calendar tc = mTask.getCalendar();
            new DatePickerDialog(getActivity(), datePickerListener,
                    tc.get(Calendar.YEAR), tc.get(Calendar.MONTH), tc.get(Calendar.DAY_OF_MONTH)).show();
        });
        timeBtn.setOnClickListener(v -> {
            new TimePickerDialog(getActivity(), timePickerListener, 0, 0, true).show();
        });
        typeBtn.setOnClickListener(v -> {
            mTask.setStrict(!mTask.isStrict());
            if (mTask.isStrict())
                typeBtn.setText(R.string.title_task_type_strict);
            else
                typeBtn.setText(R.string.title_task_type_lax);

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
}

