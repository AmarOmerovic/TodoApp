package com.amaromerovic.todoister.util;

import android.content.res.Resources;

import androidx.room.TypeConverter;

import com.amaromerovic.todoister.model.Priority;

import java.util.Date;

public class Converter {

    @TypeConverter
    public static Date timeStampToDate(Long date) {
        return date == null ? null : new Date(date);
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String priorityToString(Priority priority) {
        return priority == null ? null : priority.name();
    }

    @TypeConverter
    public static Priority stringToPriority(String priority) {
        return priority == null ? null : Priority.valueOf(priority);
    }


    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


}
