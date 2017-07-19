package com.example.android.architecture.blueprints.todoapp.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.*;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksPersistenceContract;

import java.util.List;

/**
 * Created by schava on 6/20/2017.
 */

@Dao
public interface TaskDao {

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallback {

        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    @Query("SELECT COUNT(*) FROM " + TasksPersistenceContract.TaskEntry.TABLE_NAME)
    int count();

    @Query("SELECT * FROM task")
    List<Task> getTasks();

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task WHERE entryid LIKE :taskId")
    Task getTask(@NonNull String taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTask(@NonNull Task task);

    @Update (onConflict = OnConflictStrategy.ABORT)
    void completeTask(@NonNull Task task);

    @Update (onConflict = OnConflictStrategy.ABORT)
    void activateTask(@NonNull Task task);

    @Query("DELETE FROM " + TasksPersistenceContract.TaskEntry.TABLE_NAME + " WHERE " + TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED + " LIKE 1")
    void clearCompletedTasks();


    @Query("DELETE FROM " +TasksPersistenceContract.TaskEntry.TABLE_NAME)
    void deleteAllTasks();

    @Query("DELETE FROM " + TasksPersistenceContract.TaskEntry.TABLE_NAME + " WHERE " + TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID + " = :taskId")
    int deleteTaskById(@NonNull String taskId);

    @Delete
    void deleteTask(@NonNull Task task);
}
