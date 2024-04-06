package com.bawp.todoister;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawp.todoister.data.Priority;
import com.bawp.todoister.data.Task;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.TaskViewModel;
import com.bawp.todoister.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText entertodo;
    private ImageButton calenderButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calenderGroup;
    private Date DueDate;
    private SharedViewModel sharedViewModel;
    Calendar calendar = Calendar.getInstance();
    private boolean is_Edit;
    private Priority priority;


    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        calendarView = view.findViewById(R.id.calendar_view);
        calenderGroup = view.findViewById(R.id.calendar_group);
        calenderButton = view.findViewById(R.id.today_calendar_button);
        entertodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip =view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextweekChip =view.findViewById(R.id.next_week_chip);
        nextweekChip.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedViewModel.getSelectedItem().getValue()!=null) {
            Task task = sharedViewModel.getSelectedItem().getValue();
            entertodo.setText(task.getName());
            is_Edit = sharedViewModel.get_Is_Edit();
            Log.d("SHHHHH", "onViewCreated: "+sharedViewModel.getSelectedItem().getValue().getName());
        }

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        calenderButton.setOnClickListener(v -> {
            calenderGroup.setVisibility(calenderGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.HideSoftKeyboard(v);


        });

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            //Log.d("Date", "onViewCreated: month ==>"+(month+1)+", dayOFMonth :-"+dayOfMonth);
            calendar.set(year,month,dayOfMonth);
            DueDate = calendar.getTime();
        });
        priorityButton.setOnClickListener(v -> {
            Utils.HideSoftKeyboard(v);
            priorityRadioGroup.setVisibility(priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE: View.GONE);
            priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(priorityRadioGroup.getVisibility() == View.VISIBLE) {
                        selectedButtonId =checkedId;
                        selectedRadioButton = view.findViewById(selectedButtonId);
                        if(selectedRadioButton.getId() == R.id.radioButton_high){
                        priority = Priority.HIGH;
                        } else if(selectedRadioButton.getId() == R.id.radioButton_med){
                            priority = Priority.MEDIUM;
                        } else if(selectedRadioButton.getId()==R.id.radioButton_low) {
                            priority = Priority.LOW;
                        } else {
                            priority = Priority.LOW;
                        }
                    } else {
                        priority =Priority.LOW;
                    }
                }
            });

        });

        saveButton.setOnClickListener(v -> {
            String task = entertodo.getText().toString().trim();
            if(!TextUtils.isEmpty(task) && DueDate!=null && priority!=null) {
                Task myTask = new Task(task, priority,DueDate,Calendar.getInstance().getTime(),false);
                if(is_Edit) {
                    Task updatetask = sharedViewModel.getSelectedItem().getValue();
                    updatetask.setName(task);
                    updatetask.setCreationDate(Calendar.getInstance().getTime());
                    updatetask.setPriority(Priority.HIGH);
                    updatetask.setDue_Date(DueDate);
                    TaskViewModel.Update(updatetask);
                    sharedViewModel.set_Is_Edit(false);
                } else {
                    TaskViewModel.insert(myTask);
                }
                entertodo.setText("");
                if(this.isVisible()) {
                    this.dismiss();
                }
            } else {
                Snackbar.make(saveButton,R.string.empty_field,Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.today_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            DueDate = calendar.getTime();
            Log.d("DueDate", "onClick: "+DueDate.toString());
        } else if (id == R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            DueDate = calendar.getTime();
            Log.d("DueDate", "onClick: "+DueDate.toString());
        } else if(id ==R.id.next_week_chip) {
            calendar.add(Calendar.DAY_OF_YEAR,7);
            DueDate = calendar.getTime();
            Log.d("DueDate", "onClick: "+DueDate.toString());
        }

    }
}