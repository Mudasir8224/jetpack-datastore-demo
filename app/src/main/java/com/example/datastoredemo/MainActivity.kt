package com.example.datastoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var myPref: MyPreference
    private var button:Button? = null
    private var et:EditText? = null
    private var etId:EditText? = null
    private var tv:TextView? = null
    private var tvId:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        clickAction()
        getSavedValue()
    }

    private fun initViews(){
        myPref = MyPreference(this)
        et = findViewById(R.id.et)
        etId = findViewById(R.id.etId)
        tv = findViewById(R.id.tv)
        tvId = findViewById(R.id.tvId)
        button = findViewById(R.id.button)

    }

    private fun getSavedValue(){
        myPref.notes.asLiveData().observe(this, {
            tv?.text = it
        })

        myPref.notesId.asLiveData().observe(this, {
            tvId?.text = it.toString()
        })
    }

    private fun clickAction(){
        button?.setOnClickListener {
            if (validation()){
                saveNotes()
            }
        }
    }

    private fun validation():Boolean{
        return when {
            et?.text!!.isEmpty() -> {
                Toast.makeText(this, "Please Enter Content", Toast.LENGTH_LONG).show()
                false
            }
            etId?.text!!.isEmpty() -> {
                Toast.makeText(this, "Please Enter Id", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                true
            }
        }
    }

    private fun saveNotes(){

            val notes = et?.text.toString().trim()
            lifecycleScope.launch { myPref
                .saveNotes(notes)}

            val notesId = etId?.text.toString().toLong()
            lifecycleScope.launch { myPref
                .saveNotesId(notesId)}

        myPref.notes.asLiveData().observe(this, {
            tv?.text = it
        })

        myPref.notesId.asLiveData().observe(this, {
            tvId?.text = it.toString()
        })

    }

}