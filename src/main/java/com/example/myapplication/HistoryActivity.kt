package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyListView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val items: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyListView = findViewById(R.id.historyList)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        historyListView.adapter = adapter

        loadHistory()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_all -> {
                clearHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadHistory() {
        items.clear()
        val prefs = getSharedPreferences("table_history", Context.MODE_PRIVATE)
        val saved = prefs.getStringSet("numbers", emptySet()) ?: emptySet()
        // Show in reverse (latest first)
        items.addAll(saved.sortedByDescending { it.toIntOrNull() ?: 0 }.map { "Table of $it" })
        adapter.notifyDataSetChanged()
    }

    private fun clearHistory() {
        val prefs = getSharedPreferences("table_history", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        loadHistory()
        Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show()
    }
}


