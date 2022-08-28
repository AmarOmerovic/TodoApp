package com.amaromerovic.todoister;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amaromerovic.todoister.adapter.RecyclerViewAdapterDoneActivity;
import com.amaromerovic.todoister.databinding.ActivityDoneTasksBinding;
import com.amaromerovic.todoister.model.Task;
import com.amaromerovic.todoister.model.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class DoneTaskActivity extends AppCompatActivity implements RecyclerViewAdapterDoneActivity.CheckToDeleteClick {
    private ActivityDoneTasksBinding doneTasksBinding;
    private RecyclerViewAdapterDoneActivity recyclerViewAdapterDoneActivity;
    private TaskViewModel taskViewModel;
    private List<Task> deleteItems;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_tasks);

        doneTasksBinding = DataBindingUtil.setContentView(this, R.layout.activity_done_tasks);
        setSupportActionBar(doneTasksBinding.toolbar);
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TaskViewModel.class);
        deleteItems = new ArrayList<>();

        doneTasksBinding.recyclerView.setHasFixedSize(true);
        doneTasksBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskViewModel.getDoneTasks().observe(this, tasks -> {
            recyclerViewAdapterDoneActivity = new RecyclerViewAdapterDoneActivity(tasks, this);
            doneTasksBinding.recyclerView.setAdapter(recyclerViewAdapterDoneActivity);
        });

        doneTasksBinding.deleteButton.setOnClickListener(view -> {
            for (Task task : deleteItems) {
                TaskViewModel.deleteTask(task);
            }
        });


        doneTasksBinding.selectAll.setOnClickListener(view -> {
            recyclerViewAdapterDoneActivity.setChecked(true);
            recyclerViewAdapterDoneActivity.notifyDataSetChanged();
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void addToDeleteList(Task task, boolean isChecked) {
        if (isChecked) {
            deleteItems.add(task);
        } else {
            deleteItems.remove(task);
        }

    }
}