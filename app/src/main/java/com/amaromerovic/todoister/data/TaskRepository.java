package com.amaromerovic.todoister.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.amaromerovic.todoister.model.Task;
import com.amaromerovic.todoister.util.TaskRoomDatabase;

import java.util.List;

public class TaskRepository {
    private final TaskDAO taskDAO;
    private final LiveData<List<Task>> doneTasks;
    private final LiveData<List<Task>> unDoneTasks;

    public TaskRepository(Application application) {
        TaskRoomDatabase taskRoomDatabase = TaskRoomDatabase.getInstance(application.getApplicationContext());
        taskDAO = taskRoomDatabase.taskDAO();
        doneTasks = taskDAO.getAllDoneTasks();
        unDoneTasks = taskDAO.getAllUndoneTasks();
    }

    public LiveData<List<Task>> getAllDoneTasks() {
        return doneTasks;
    }

    public LiveData<List<Task>> getUnDoneTasks() {
        return unDoneTasks;
    }

    public LiveData<List<Task>> getTasks(long id) {
        return taskDAO.getTask(id);
    }

    public void deleteAllTasks() {
        TaskRoomDatabase.executorService.execute(taskDAO::deleteAllTasks);
    }

    public void deleteTask(Task task) {
        TaskRoomDatabase.executorService.execute(() -> taskDAO.deleteTask(task));
    }

    public void updateTask(Task task) {
        TaskRoomDatabase.executorService.execute(() -> taskDAO.updateTask(task));
    }

    public void addTask(Task task) {
        TaskRoomDatabase.executorService.execute(() -> taskDAO.addTask(task));
    }

}
