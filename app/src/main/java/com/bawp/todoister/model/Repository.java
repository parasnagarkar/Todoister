package com.bawp.todoister.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.todoister.data.Task;
import com.bawp.todoister.util.TaskRoomDataBase;
import com.bawp.todoister.util.Task_Dao;

import java.util.List;

public class Repository {
    private final Task_Dao taskDao;
    private final LiveData<List<Task>> ALLTASKS;

    public Repository(Application application) {
        TaskRoomDataBase db = TaskRoomDataBase.getDataBase(application);
        taskDao = db.taskDao();
        ALLTASKS = taskDao.GEtAllTask();

    }

    public LiveData<List<Task>> GETALLTASKS() { return ALLTASKS;}


    public void insert(Task task) { TaskRoomDataBase.DataWriteExecutor.execute(()->{
        taskDao.insert(task);
    });}


    public void deleteALl() { taskDao.DeleteAll();}


    public void Delete(Task task) { TaskRoomDataBase.DataWriteExecutor.execute(()->{
        taskDao.Delete(task);
    });}

    public void  Update(Task task) { TaskRoomDataBase.DataWriteExecutor.execute(()->{
        taskDao.Update(task);
    });}


    public LiveData<Task> getTask(long id) { return taskDao.getTask(id);}
}
