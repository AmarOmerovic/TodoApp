package com.amaromerovic.todoister.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amaromerovic.todoister.data.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private static TaskRepository taskRepository;
    private final LiveData<List<Task>> doneTasks;
    private final LiveData<List<Task>> unDoneTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        doneTasks = taskRepository.getAllDoneTasks();
        unDoneTasks = taskRepository.getUnDoneTasks();
    }

    public LiveData<List<Task>> getDoneTasks() {
        return doneTasks;
    }

    public LiveData<List<Task>> getUnDoneTasks() {
        return unDoneTasks;
    }

    public LiveData<List<Task>> getTask(long id) {
        return taskRepository.getTasks(id);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAllTasks();
    }

    public static void deleteTask(Task task) {
        taskRepository.deleteTask(task);
    }

    public static void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    public static void addTask(Task task) {
        taskRepository.addTask(task);
    }

}
