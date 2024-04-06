package com.bawp.todoister.Adapter;



import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.todoister.R;
import com.bawp.todoister.data.Task;
import com.bawp.todoister.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> ALLTask;
    private final ToDoOnClickListner toDoOnClickListner;

    public RecyclerViewAdapter(List<Task> allTask,ToDoOnClickListner toDoOnClickListner) {
        ALLTask = allTask;
        this.toDoOnClickListner = toDoOnClickListner;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,parent,false);

        return new ViewHolder(view,toDoOnClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = ALLTask.get(position);
        String formatted = Utils.formatDate(task.getDue_Date());

        ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{-android.R.attr.state_enabled},new int[]{android.R.attr.state_enabled}},new int[]{Color.LTGRAY,
        Utils.priorityColor(task)});
        holder.TaskName.setText(task.Name);
        holder.todayChip.setText(formatted);
        holder.todayChip.setTextColor(Utils.priorityColor(task));
        holder.todayChip.setChipIconTint(colorStateList);
        holder.radioButton.setButtonTintList(colorStateList);

    }

    @Override
    public int getItemCount() {
        return ALLTask.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatRadioButton radioButton;
        public AppCompatTextView TaskName;
        public Chip todayChip;
        ToDoOnClickListner toDoOnClickListner;
        public ViewHolder(@NonNull View itemView,ToDoOnClickListner toDoOnClickListner) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            radioButton.setOnClickListener(this);
            TaskName = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            this.toDoOnClickListner = toDoOnClickListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id =v.getId();
            Task currTask = ALLTask.get(getAdapterPosition());
            if(id==R.id.todo_row_layout)
                toDoOnClickListner.onToDoCLickListner(getAdapterPosition(),currTask);
            else if(id==R.id.todo_radio_button)
                toDoOnClickListner.OnClickRadioButtonListner(currTask);
        }
    }
}
