package com.bawp.todoister.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Task_Manager")
public class Task {
    @ColumnInfo(name = "Task_ID")
    @PrimaryKey(autoGenerate = true)
    public long Task_ID;

    @ColumnInfo(name = "Task_Name")
    public String Name;

    public Priority priority;
    @ColumnInfo(name = "Due_Date")
    public Date Due_Date;

    @ColumnInfo(name = "Creation_Date")
    public Date CreationDate;

    @ColumnInfo(name = "IS_Done")
    public boolean is_Done;

    public Task(){}
    public Task(String name, Priority priority, Date due_Date, Date creationDate, boolean is_Done) {
        Name = name;
        this.priority = priority;
        Due_Date = due_Date;
        CreationDate = creationDate;
        this.is_Done = is_Done;
    }

    public long getTask_ID() {
        return Task_ID;
    }

    public void setTask_ID(long task_ID) {
        Task_ID = task_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDue_Date() {
        return Due_Date;
    }

    public void setDue_Date(Date due_Date) {
        Due_Date = due_Date;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public boolean isIs_Done() {
        return is_Done;
    }

    public void setIs_Done(boolean is_Done) {
        this.is_Done = is_Done;
    }
}
