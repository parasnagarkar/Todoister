package com.bawp.todoister.util;

import androidx.room.TypeConverter;

import com.bawp.todoister.data.Priority;

import java.util.Date;

public class Converter {

    @TypeConverter
    public static Date FromTimeStamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long DateToTimeStamo(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String FromPriority(Priority priority){
        return priority == null ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String s){
        return s == null ? null : Priority.valueOf(s);
    }

}
