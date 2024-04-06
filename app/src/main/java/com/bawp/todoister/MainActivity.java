package com.bawp.todoister;

import android.os.Bundle;

import com.bawp.todoister.Adapter.RecyclerViewAdapter;
import com.bawp.todoister.Adapter.ToDoOnClickListner;
import com.bawp.todoister.data.Priority;
import com.bawp.todoister.data.Task;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoOnClickListner {
    private TaskViewModel TVM;
    private RecyclerViewAdapter RVA;
    private RecyclerView RV;
    BottomSheetFragment bottomSheetFragment;
    private SharedViewModel SVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RV = findViewById(R.id.recycler_view);
        FloatingActionButton fab = findViewById(R.id.fab);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));
        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);
        SVM = new ViewModelProvider(this).get(SharedViewModel.class);


        TVM = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(TaskViewModel.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Task task = new Task("Dinner", Priority.HIGH, Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),false);
//                TaskViewModel.insert(task);
                showBottomSheetDialog();

            }
        });
        TVM.GetAllTasks().observe(this, tasks -> {
            RVA = new RecyclerViewAdapter(tasks,this);
            RV.setAdapter(RVA);
        });
    }
    private void showBottomSheetDialog(){
        bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onToDoCLickListner(int position, Task task) {
        SVM.setSelectedTask(task);
        Log.d("Listner", "onToDoCLickListner: "+position + ",Name ==>"+task.getName());
        SVM.set_Is_Edit(true);
        showBottomSheetDialog();
    }

    @Override
    public void OnClickRadioButtonListner(Task t) {
        Log.d("Dinner", "OnClickRadioButtonListner: "+t.getName());
        TaskViewModel.Delete(t);
        RVA.notifyDataSetChanged();
    }
}