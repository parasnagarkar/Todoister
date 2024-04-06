package com.bawp.todoister.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bawp.todoister.data.Task;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Task> SelectedTask = new MutableLiveData<Task>();
    private boolean is_EDit;

    public void setSelectedTask(Task task) { SelectedTask.setValue(task);}
    public LiveData<Task> getSelectedItem(){return SelectedTask;}

    public boolean get_Is_Edit() {
        return is_EDit;
    }

    public void set_Is_Edit(boolean is_EDit) {
        this.is_EDit = is_EDit;
    }
}
