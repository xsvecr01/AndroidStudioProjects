package com.example.hexapodcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle;
import android.widget.CheckBox;

class MainActivity : AppCompatActivity() {

    CheckBox enable_bt, visible_bt;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}