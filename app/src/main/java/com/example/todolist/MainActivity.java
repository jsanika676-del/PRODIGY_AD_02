package com.example.todolist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTask;
    Button btnAdd;
    ListView taskList;

    ArrayList<String> tasks;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTask = findViewById(R.id.editTask);
        btnAdd = findViewById(R.id.btnAdd);
        taskList = findViewById(R.id.taskList);

        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tasks);

        taskList.setAdapter(adapter);

        // Add Task
        btnAdd.setOnClickListener(v -> {
            String task = editTask.getText().toString();
            if (!task.isEmpty()) {
                tasks.add(task);
                adapter.notifyDataSetChanged();
                editTask.setText("");
            } else {
                Toast.makeText(this, "Enter task", Toast.LENGTH_SHORT).show();
            }
        });

        // Edit / Delete Task
        taskList.setOnItemLongClickListener((parent, view, position, id) -> {
            showOptions(position);
            return true;
        });
    }

    private void showOptions(int position) {
        String[] options = {"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");

        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                editTask.setText(tasks.get(position));
                tasks.remove(position);
                adapter.notifyDataSetChanged();
            } else {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }
}