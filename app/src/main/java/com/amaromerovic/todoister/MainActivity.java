package com.amaromerovic.todoister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amaromerovic.todoister.adapter.OnToDoClickListener;
import com.amaromerovic.todoister.adapter.RecyclerViewAdapterMainActivity;
import com.amaromerovic.todoister.databinding.ActivityMainBinding;
import com.amaromerovic.todoister.model.SharedViewModel;
import com.amaromerovic.todoister.model.Task;
import com.amaromerovic.todoister.model.TaskViewModel;
import com.amaromerovic.todoister.ui.BottomSheetFragment;

import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements OnToDoClickListener {
    private ActivityMainBinding mainBinding;
    private TaskViewModel taskViewModel;
    private RecyclerViewAdapterMainActivity recyclerViewAdapterMainActivity;
    private BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        setSupportActionBar(mainBinding.toolbar);
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TaskViewModel.class);
        bottomSheetFragment = new BottomSheetFragment();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);


        mainBinding.recyclerView.setHasFixedSize(true);
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskViewModel.getUnDoneTasks().observe(this, tasks -> {
            recyclerViewAdapterMainActivity = new RecyclerViewAdapterMainActivity(tasks, this);
            tasks.sort(Comparator.comparing(Task::getDueDate));
            mainBinding.recyclerView.setAdapter(recyclerViewAdapterMainActivity);
        });


        mainBinding.addNewItem.setOnClickListener(view -> {
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            sharedViewModel.selectItem(null);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mainMenu) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.done) {
            Intent intent = new Intent(this, DoneTaskActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoItemClicked(Task task) {
        sharedViewModel.selectItem(task);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onIsDoneRadioButtonClicked(Task task) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            task.setDone(true);
            TaskViewModel.updateTask(task);
        }, 1500);

    }
}




