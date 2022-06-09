package com.asifddlks.samplecustomviewapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        val customEditTextView = findViewById<CustomEditTextView>(R.id.customEditTextView)
        val drawingView = findViewById<DrawingView>(R.id.drawingView)

        customEditTextView.setText("hello TVL")

        //drawingView.

    }
}