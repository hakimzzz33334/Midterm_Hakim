package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var numberInput: EditText
    private lateinit var generateBtn: Button
    private lateinit var historyBtn: Button
    private lateinit var tableList: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberInput = findViewById(R.id.numberInput)
        generateBtn = findViewById(R.id.generateBtn)
        historyBtn = findViewById(R.id.historyBtn)
        tableList = findViewById(R.id.tableList)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        tableList.adapter = adapter

        generateBtn.setOnClickListener {
            val text = numberInput.text.toString().trim()
            val number = text.toIntOrNull()
            if (number == null) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showTable(number)
            saveToHistory(number)
        }

        historyBtn.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun showTable(number: Int) {
        val rows = (1..10).map { i -> "$number x $i = ${number * i}" }
        adapter.clear()
        adapter.addAll(rows)
        adapter.notifyDataSetChanged()
    }

    private fun saveToHistory(number: Int) {
        val prefs = getSharedPreferences("table_history", Context.MODE_PRIVATE)
        val current = prefs.getStringSet("numbers", emptySet())?.toMutableSet() ?: mutableSetOf()
        current.add(number.toString())
        prefs.edit().putStringSet("numbers", current).apply()
    }
}