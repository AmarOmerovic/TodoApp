package com.amaromerovic.todoister.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.amaromerovic.todoister.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM tasks WHERE tasks.isDone = 1 ORDER BY id ASC")
    LiveData<List<Task>> getAllDoneTasks();

    @Query("SELECT * FROM tasks WHERE tasks.isDone = 0 ORDER BY id ASC")
    LiveData<List<Task>> getAllUndoneTasks();

    @Query("SELECT * FROM tasks WHERE tasks.ID == :id")
    LiveData<List<Task>> getTask(long id);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Delete
    void deleteTask(Task task);

    @Insert
    void addTask(Task task);

    @Update
    void updateTask(Task task);
}
