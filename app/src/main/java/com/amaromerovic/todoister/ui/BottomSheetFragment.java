package com.amaromerovic.todoister.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.amaromerovic.todoister.R;
import com.amaromerovic.todoister.databinding.BottomSheetBinding;
import com.amaromerovic.todoister.model.Priority;
import com.amaromerovic.todoister.model.SharedViewModel;
import com.amaromerovic.todoister.model.Task;
import com.amaromerovic.todoister.model.TaskViewModel;
import com.amaromerovic.todoister.util.Converter;
import com.amaromerovic.todoister.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetBinding bottomSheetBinding;
    private SharedViewModel sharedViewModel;
    private Priority priority;
    private String todoItem;
    private Date dueDate;
    private boolean isEdited;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false);
        return bottomSheetBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        calendar = Calendar.getInstance();
        if (sharedViewModel.getSelectedItem().getValue() == null) {
            isEdited = false;
            priority = Priority.LOW;
        } else {
            isEdited = true;
            sharedViewModel.getSelectedItem().observe(this, task -> {
                todoItem = task.getTaskText();
                bottomSheetBinding.todoText.setText(todoItem);
                bottomSheetBinding.highPrioRadioButton.setChecked(task.getPriority() == Priority.HIGH);
                bottomSheetBinding.mediumPrioRadioButton.setChecked(task.getPriority() == Priority.MEDIUM);
                bottomSheetBinding.lowPrioRadioButton.setChecked(task.getPriority() == Priority.LOW);
                priority = task.getPriority();
                bottomSheetBinding.calendarView.setDate(task.getDueDate().getTime());
                dueDate = task.getDueDate();
            });

        }

        bottomSheetBinding.calendarButton.setOnClickListener(buttonView -> {
            if (bottomSheetBinding.radioGroup.getVisibility() == View.GONE) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bottomSheetBinding.dueTextView.getLayoutParams();
                params.topMargin = Converter.dpToPx(24);
                view.requestLayout();
            }
            if (bottomSheetBinding.calendarGroup.getVisibility() == View.GONE) {
                Utils.hideSoftKeyboard(buttonView);
            }
            bottomSheetBinding.calendarGroup.setVisibility(
                    bottomSheetBinding.calendarGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
            );

        });

        bottomSheetBinding.timeButton.setOnClickListener(timeView -> {
            timePickerDialog = new TimePickerDialog(getContext(),
                    (timePickerView, hourOfDay, minute) -> {
                        calendar.set(Calendar.HOUR, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                    }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false);

            timePickerDialog.show();

        });

        bottomSheetBinding.priorButton.setOnClickListener(buttonView -> {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bottomSheetBinding.dueTextView.getLayoutParams();
            if (bottomSheetBinding.radioGroup.getVisibility() == View.VISIBLE) {
                params.topMargin = Converter.dpToPx(24);
            } else {
                Utils.hideSoftKeyboard(buttonView);
                params.topMargin = Converter.dpToPx(96);
            }
            view.requestLayout();

            bottomSheetBinding.radioGroup.setVisibility(
                    bottomSheetBinding.radioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );


        });

        bottomSheetBinding.highPrioRadioButton.setOnClickListener(prioButtonView -> priority = Priority.HIGH);
        bottomSheetBinding.mediumPrioRadioButton.setOnClickListener(prioButtonView -> priority = Priority.MEDIUM);
        bottomSheetBinding.lowPrioRadioButton.setOnClickListener(prioButtonView -> priority = Priority.LOW);
        bottomSheetBinding.todayChip.setOnClickListener(dateChipView -> dueDate = todayTomorrowNextWeek(0));
        bottomSheetBinding.tomorrowChip.setOnClickListener(dateChipView -> dueDate = todayTomorrowNextWeek(1));
        bottomSheetBinding.nextWeekChip.setOnClickListener(dateChipView -> dueDate = todayTomorrowNextWeek(7));
        bottomSheetBinding.calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
        });


        bottomSheetBinding.saveButton.setOnClickListener(buttonView -> {
            todoItem = bottomSheetBinding.todoText.getText().toString().trim();

            if (todoItem.isEmpty() || priority == null || calendar == null) {
                dismiss();
                return;
            }

            dueDate = calendar.getTime();
            if (!isEdited) {
                TaskViewModel.addTask(new Task(todoItem, priority, dueDate, Calendar.getInstance().getTime(), false));
            } else {
                sharedViewModel.getSelectedItem().observe(this, task -> {
                    task.setTaskText(todoItem);
                    task.setDueDate(dueDate);
                    task.setPriority(priority);
                    TaskViewModel.updateTask(task);
                });
            }

            dismiss();
            hideGroups();
            clearedOptions();
        });

    }

    private void hideGroups() {
        bottomSheetBinding.radioGroup.setVisibility(View.GONE);
        bottomSheetBinding.calendarGroup.setVisibility(View.GONE);
    }

    private Date todayTomorrowNextWeek(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    private void clearedOptions() {
        bottomSheetBinding.highPrioRadioButton.setChecked(false);
        bottomSheetBinding.mediumPrioRadioButton.setChecked(false);
        bottomSheetBinding.lowPrioRadioButton.setChecked(false);
        bottomSheetBinding.todoText.setText("");
    }

    @Override
    public void onPause() {
        super.onPause();
        clearedOptions();
    }
}
