package com.example.midterm_firstname_lastname;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyList;
    private ArrayAdapter<String> adapter;
    private final List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = findViewById(R.id.historyList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        historyList.setAdapter(adapter);

        loadHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear_all) {
            clearHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadHistory() {
        items.clear();
        Set<String> saved = getSharedPreferences("table_history", Context.MODE_PRIVATE)
                .getStringSet("numbers", new HashSet<>());
        List<Integer> nums = new ArrayList<>();
        for (String s : saved) {
            try { nums.add(Integer.parseInt(s)); } catch (Exception ignored) {}
        }
        Collections.sort(nums, Collections.reverseOrder());
        for (Integer n : nums) {
            items.add("Table of " + n);
        }
        adapter.notifyDataSetChanged();
    }

    private void clearHistory() {
        getSharedPreferences("table_history", Context.MODE_PRIVATE).edit().clear().apply();
        loadHistory();
        Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
    }
}


