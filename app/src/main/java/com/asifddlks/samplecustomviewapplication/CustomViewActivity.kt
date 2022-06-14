package com.asifddlks.samplecustomviewapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        val customChartView = findViewById<CustomChartView>(R.id.customChartView)
        val customEditTextView = findViewById<CustomEditTextView>(R.id.customEditTextView)
        val multiTouchView = findViewById<MultiTouchView>(R.id.multiTouchView)

        customEditTextView.setText("hello TVL")

        /*val canvas = Canvas()
        canvas.drawColor(Color.WHITE)
        Paint().color = Color.GRAY
        Paint().textSize = 50f
        Paint().textAlign = Paint.Align.CENTER
        canvas.drawText("HelloWorld", width.toFloat()/2, height.toFloat()-context.resources.displayMetrics.density*20, paint)

        customChartView.draw()*/

        val dataList:MutableList<ChartModel> = ArrayList()
        dataList.add(ChartModel(0f,100f))
        dataList.add(ChartModel(200f, 380f   ))
        dataList.add(ChartModel(450f,540f, ))
        dataList.add(ChartModel(650f,400f ))
        dataList.add(ChartModel(850f,720f,))

        customChartView.drawChart(dataList)
        multiTouchView.drawChart(dataList)

    }
}