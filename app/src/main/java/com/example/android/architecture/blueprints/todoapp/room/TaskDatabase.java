package com.example.android.architecture.blueprints.todoapp.room;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import java.util.LinkedHashMap;
import java.util.Map;


@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase sInstance;

    private final static Map<String, Task> TASKS_SERVICE_DATA =  new LinkedHashMap<>(2);

    public abstract TaskDao taskDao();

    public static synchronized TaskDatabase getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), TaskDatabase.class, "TaskSample")
                    //.allowMainThreadQueries() // Allow Main thread ????
                    .build();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                }
            });
            t.start();
        }
        return sInstance;
    }

    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                TaskDatabase.class).build();
    }

    public void addTask(String title, String description) {
        Task newTask = new Task(title, description);
        TASKS_SERVICE_DATA.put(newTask.getId(), newTask);

        sInstance.taskDao().saveTask(newTask);
    }
}



