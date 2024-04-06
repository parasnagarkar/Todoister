package com.bawp.todoister.util;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.todoister.data.Task;

import java.util.List;

@Dao
public interface Task_Dao {
    @Insert
    void insert(Task task);

    @Query("Delete From task_manager")
    void DeleteAll();

    @Query("Select * From Task_Manager")
    LiveData<List<Task>> GEtAllTask();


    @Update
    void Update(Task task);

    @Delete
    void Delete(Task task);
    @Query("Select * From Task_Manager where Task_Manager.Task_ID=:id")
    LiveData<Task> getTask(long id);




}
