package com.amaromerovic.todoister.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.amaromerovic.todoister.model.Priority;
import com.amaromerovic.todoister.model.Task;

public class Utils {

    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int priorityColor(Task task) {
        int color;
        if (task.getPriority() == Priority.HIGH) {
            color = Color.rgb(244, 67, 54);
        } else if (task.getPriority() == Priority.MEDIUM) {
            color = Color.rgb(56, 142, 60);
        } else {
            color = Color.rgb(48, 63, 159);
        }
        return color;
    }
}