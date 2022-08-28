package com.amaromerovic.todoister.adapter;

import com.amaromerovic.todoister.model.Task;

public interface OnToDoClickListener {
    void onTodoItemClicked(Task task);

    void onIsDoneRadioButtonClicked(Task task);
}