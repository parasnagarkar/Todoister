package com.bawp.todoister.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.todoister.data.Task;


import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private static  Repository repository;
    private final LiveData<List<Task>> ALLTASKS;
    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        ALLTASKS = repository.GETALLTASKS();
    }
    public static void insert(Task task) { repository.insert(task);}

    public static void Update(Task task) { repository.Update(task);}
    public static void DeleteAll() { repository.deleteALl();}
    public static void Delete(Task task) { repository.Delete(task);}

    public LiveData<List<Task>> GetAllTasks() { return repository.GETALLTASKS();}

    public  LiveData<Task> Get(long id) { return repository.getTask(id);}
}
