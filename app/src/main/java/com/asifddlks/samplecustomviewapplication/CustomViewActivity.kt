package com.asifddlks.samplecustomviewapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        val customEditTextView = findViewById<CustomEditTextView>(R.id.customEditTextView)

        customEditTextView.setText("hello TVL")

    }
}