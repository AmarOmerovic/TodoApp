package com.amaromerovic.todoister.adapter;


import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amaromerovic.todoister.databinding.DeleteRowBinding;
import com.amaromerovic.todoister.model.Task;

import java.util.List;
import java.util.Locale;


public class RecyclerViewAdapterDoneActivity extends RecyclerView.Adapter<RecyclerViewAdapterDoneActivity.ViewHolder> {
    private final List<Task> tasks;
    private final CheckToDeleteClick checkToDeleteClick;
    private boolean isChecked;


    public RecyclerViewAdapterDoneActivity(List<Task> tasks, CheckToDeleteClick checkToDeleteClick) {
        this.tasks = tasks;
        this.checkToDeleteClick = checkToDeleteClick;
        isChecked = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DeleteRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.deleteRowBinding.todoTask.setText(tasks.get(position).getTaskText());
        holder.deleteRowBinding.dateChip.setText(new SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(tasks.get(position).getDueDate()));
        holder.deleteRowBinding.time.setText(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(tasks.get(position).getDueDate()));
        holder.deleteRowBinding.markToDeleteButton.setOnCheckedChangeListener((compoundButton, b) -> {
            b = compoundButton.isChecked();
            holder.checkToDeleteClick.addToDeleteList(tasks.get(holder.getAdapterPosition()), b);
        });

        holder.deleteRowBinding.markToDeleteButton.setChecked(isChecked);

    }


    public void setChecked(boolean checked) {
        if (isChecked == checked) {
            isChecked = false;
        } else {
            isChecked = checked;
        }

    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final DeleteRowBinding deleteRowBinding;
        private final CheckToDeleteClick checkToDeleteClick;

        public ViewHolder(@NonNull DeleteRowBinding deleteRowBinding) {
            super(deleteRowBinding.getRoot());
            this.deleteRowBinding = deleteRowBinding;
            this.checkToDeleteClick = RecyclerViewAdapterDoneActivity.this.checkToDeleteClick;
        }

    }

    public interface CheckToDeleteClick {
        void addToDeleteList(Task task, boolean isChecked);
    }

}
