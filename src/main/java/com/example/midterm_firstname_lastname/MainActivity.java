package com.example.midterm_firstname_lastname;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private EditText numberInput;
    private Button generateBtn;
    private Button historyBtn;
    private ListView tableList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput = findViewById(R.id.numberInput);
        generateBtn = findViewById(R.id.generateBtn);
        historyBtn = findViewById(R.id.historyBtn);
        tableList = findViewById(R.id.tableList);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        tableList.setAdapter(adapter);

        generateBtn.setOnClickListener(v -> {
            String text = numberInput.getText().toString().trim();
            Integer number = null;
            try { number = Integer.parseInt(text); } catch (Exception ignored) {}
            if (number == null) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                return;
            }
            showTable(number);
            saveToHistory(number);
        });

        historyBtn.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
    }

    private void showTable(int number) {
        List<String> rows = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            rows.add(number + " x " + i + " = " + (number * i));
        }
        adapter.clear();
        adapter.addAll(rows);
        adapter.notifyDataSetChanged();
    }

    private void saveToHistory(int number) {
        Set<String> current = new HashSet<>(getSharedPreferences("table_history", Context.MODE_PRIVATE)
                .getStringSet("numbers", new HashSet<>()));
        current.add(String.valueOf(number));
        getSharedPreferences("table_history", Context.MODE_PRIVATE)
                .edit()
                .putStringSet("numbers", current)
                .apply();
    }
}


