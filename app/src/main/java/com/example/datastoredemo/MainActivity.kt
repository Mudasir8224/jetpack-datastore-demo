package com.example.datastoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var myPref: MyPreference
    private var button:Button? = null
    private var et:EditText? = null
    private var tv:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        saveNotes()
    }

    private fun initViews(){
        myPref = MyPreference(this)
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        button = findViewById(R.id.button);
    }

    private fun saveNotes(){
        button?.setOnClickListener {
            val notes = et?.text.toString().trim()
            lifecycleScope.launch { myPref
                .saveNotes(notes)}
        }

        myPref.notes.asLiveData().observe(this, Observer {
            tv?.text = it
        })

    }

}