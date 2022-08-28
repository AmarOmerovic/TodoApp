package com.amaromerovic.todoister.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;

    @ColumnInfo(name = "Task")
    private String taskText;

    @ColumnInfo(name = "Priority")
    private Priority priority;

    @ColumnInfo(name = "Due date")
    private Date dueDate;

    @ColumnInfo(name = "Created at")
    private Date cratedAt;

    private boolean isDone;

    public Task(@NonNull String taskText, @NotNull Priority priority, @NotNull Date dueDate, @NonNull Date cratedAt, boolean isDone) {
        this.taskText = taskText;
        this.priority = priority;
        this.dueDate = dueDate;
        this.cratedAt = cratedAt;
        this.isDone = isDone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCratedAt() {
        return cratedAt;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskText='" + taskText + '\'' +
                ", priority=" + priority +
                ", dueString=" + dueDate +
                ", cratedAt=" + cratedAt +
                ", isDone=" + isDone +
                '}';
    }
}
