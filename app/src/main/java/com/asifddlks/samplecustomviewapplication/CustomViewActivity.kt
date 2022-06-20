package com.asifddlks.samplecustomviewapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asifddlks.samplecustomviewapplication.customView.CustomChartView
import com.asifddlks.samplecustomviewapplication.customView.CustomEditTextView
import com.asifddlks.samplecustomviewapplication.customView.MultiTouchView
import com.asifddlks.samplecustomviewapplication.utility.FileHelper
import org.json.JSONObject

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

        /*val dataList:MutableList<ChartModel> = ArrayList()

        dataList.add(ChartModel(0f,200f))
        dataList.add(ChartModel(50f,0f))
        dataList.add(ChartModel(100f,100f))
        dataList.add(ChartModel(200f, 380f   ))
        dataList.add(ChartModel(450f,540f, ))
        dataList.add(ChartModel(650f,400f ))
        dataList.add(ChartModel(850f,820f,))
        dataList.add(ChartModel(950f,720f,))

        customChartView.drawChart(dataList)
        multiTouchView.drawChart(dataList)*/

        val dataList = prepareCustomData()

        customChartView.drawChart(dataList)

        multiTouchView.drawChart(dataList, object : TouchPointInteractor{
            override fun touchOne(nearestPointIndex: Int, chartModel: ChartModel) {

            }

            override fun touchTwo(nearestPointIndex: Int, chartModel: ChartModel) {

            }


        })

    }

    private fun prepareCustomData(): ArrayList<ChartModel>{
        val list: ArrayList<ChartModel> = ArrayList()
        val jsonString:String? = FileHelper().readJSONFile(this,"stockx_dummy_data_day")
        val jsonObject = jsonString?.let { JSONObject(it) }
        val dataJSONArray = jsonObject?.getJSONArray("data")

        for (i in 0 until (dataJSONArray?.length() ?: 0)) {
            val jsonObjectDataItem = dataJSONArray!!.getJSONObject(i)

            val time:String = jsonObjectDataItem.getString("time")
            val closePrice:Double = jsonObjectDataItem.getDouble("close")

            list.add(ChartModel(time,closePrice))
        }
        return list
    }
}

interface TouchPointInteractor{
    fun touchOne(nearestPointIndex: Int, chartModel: ChartModel)
    fun touchTwo(nearestPointIndex: Int, chartModel: ChartModel)
}