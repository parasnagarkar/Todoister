package com.bawp.todoister.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bawp.todoister.data.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class},version = 1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TaskRoomDataBase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 4;
    public abstract Task_Dao taskDao();
    private static volatile TaskRoomDataBase Instance;

    public static final ExecutorService DataWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TaskRoomDataBase getDataBase(final Context context) {
        if (Instance==null) {
            synchronized (TaskRoomDataBase.class) {
                if (Instance==null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(),TaskRoomDataBase.class,"Task_DataBase").addCallback(sTaskRoomDataBase).build();
                }
            }
        }
        return Instance;
    }

    public static final RoomDatabase.Callback sTaskRoomDataBase = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            DataWriteExecutor.execute(()->{
                // Use to Set Data In TaskDataBase });
                Task_Dao taskDao = Instance.taskDao();
                taskDao.DeleteAll();

            });
        }
    };
}
