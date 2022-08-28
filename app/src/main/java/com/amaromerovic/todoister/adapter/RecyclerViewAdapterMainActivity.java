package com.amaromerovic.todoister.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amaromerovic.todoister.R;
import com.amaromerovic.todoister.databinding.TodoRowBinding;
import com.amaromerovic.todoister.model.Task;
import com.amaromerovic.todoister.util.Utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapterMainActivity extends RecyclerView.Adapter<RecyclerViewAdapterMainActivity.ViewHolder> {
    private final OnToDoClickListener onToDoClickListener;
    private final List<Task> tasks;

    public RecyclerViewAdapterMainActivity(List<Task> tasks, OnToDoClickListener onToDoClickListener) {
        this.tasks = tasks;
        this.onToDoClickListener = onToDoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TodoRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        },
                new int[]{
                        Color.BLACK,
                        Utils.priorityColor(tasks.get(position)),
                });

        holder.todoRowBinding.dateChip.setTextColor(Utils.priorityColor(tasks.get(position)));
        holder.todoRowBinding.dateChip.setChipIconTint(colorStateList);
        holder.todoRowBinding.isDoneRadioButton.setButtonTintList(colorStateList);
        holder.todoRowBinding.time.setTextColor(Utils.priorityColor(tasks.get(position)));

        holder.todoRowBinding.todoTask.setText(tasks.get(position).getTaskText());
        holder.todoRowBinding.dateChip.setText(new SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(tasks.get(position).getDueDate()));
        holder.todoRowBinding.isDoneRadioButton.setChecked(tasks.get(position).isDone());
        holder.todoRowBinding.time.setText(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(tasks.get(position).getDueDate()));

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TodoRowBinding todoRowBinding;
        private final OnToDoClickListener onToDoClickListener;

        public ViewHolder(@NonNull TodoRowBinding todoRowBinding) {
            super(todoRowBinding.getRoot());
            this.todoRowBinding = todoRowBinding;
            this.onToDoClickListener = RecyclerViewAdapterMainActivity.this.onToDoClickListener;
            todoRowBinding.getRoot().setOnClickListener(this);
            todoRowBinding.isDoneRadioButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.todoRowLayout) {
                onToDoClickListener.onTodoItemClicked(tasks.get(getAdapterPosition()));
            } else if (view.getId() == R.id.isDoneRadioButton) {
                todoRowBinding.isDoneRadioButton.setChecked(true);
                onToDoClickListener.onIsDoneRadioButtonClicked(tasks.get(getAdapterPosition()));
            }

        }

    }

}
