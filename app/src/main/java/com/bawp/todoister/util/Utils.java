package com.bawp.todoister.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bawp.todoister.data.Priority;
import com.bawp.todoister.data.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String formatDate(Date date) {
        SimpleDateFormat simpleFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleFormat.applyPattern("EEE,MMM d");
        return simpleFormat.format(date);
    }
    public static void HideSoftKeyboard(View view){
        InputMethodManager IMM = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        IMM.hideSoftInputFromWindow(view.getWindowToken(),0 );

    }

    public static int priorityColor(Task task) {
        int color;
        if( task.priority == Priority.HIGH) {
            color = Color.argb(200, 201, 21, 23);
        } else if (task.priority == Priority.MEDIUM) {
            color = Color.argb(200, 155, 179, 0);
        } else  {
            color = Color.argb(200, 51, 181, 129);
        }
        return color;
    }
}
