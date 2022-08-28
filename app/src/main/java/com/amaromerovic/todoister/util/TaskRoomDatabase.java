package com.amaromerovic.todoister.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.amaromerovic.todoister.data.TaskDAO;
import com.amaromerovic.todoister.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TaskRoomDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "tasks";
    private static volatile TaskRoomDatabase INSTANCE;

    public static final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TaskRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, TaskRoomDatabase.class, DATABASE_NAME).addCallback(taskRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TaskDAO taskDAO();

    public static RoomDatabase.Callback taskRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            executorService.execute(() -> {
                TaskDAO taskDAO = INSTANCE.taskDAO();
                taskDAO.deleteAllTasks();
            });
        }
    };

}
