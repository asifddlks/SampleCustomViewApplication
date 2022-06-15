package com.asifddlks.samplecustomviewapplication.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.asifddlks.samplecustomviewapplication.ChartModel


//
// Created by Asif Ahmed on 23/5/22.
//
class CustomChartView:View {

    private var paint:Paint = Paint()

    var dataList:List<ChartModel> = ArrayList()

    var upperLimit: Float = 0f
    var lowerLimit: Float = Float.MAX_VALUE

    var constraintDifference = 0f

    constructor(context: Context, attrs: AttributeSet):super(context,attrs){
        //paint.color = Color.BLUE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(parentWidth,(upperLimit.toInt()-lowerLimit.toInt()))
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawColor(Color.WHITE)

        constraintDifference = height - upperLimit
        //constraintDifference = 0f

        //move canvas to lower limit
        //canvas.save()
        //canvas.translate(0f,lowerLimit+constraintDifference)
        //canvas.scale(2.5f,2.5f)

        paint.color = Color.DKGRAY
        paint.strokeWidth = 20f
        canvas.drawLine(0f, height - (upperLimit + constraintDifference), width.toFloat(), height - (upperLimit + constraintDifference), paint)
        canvas.drawLine(0f, height - (lowerLimit + constraintDifference), width.toFloat(), height - (lowerLimit + constraintDifference), paint)

        for (i in dataList.indices){
            paint.color = Color.GREEN
            paint.strokeWidth = 10f

            if (i == 0) {
                //Draw First Line
                val startX = dataList[i].x
                val startY = height-(dataList[i].y + constraintDifference)
                val stopX = dataList[i + 1].x
                val stopY = height-(dataList[i + 1].y + constraintDifference)

                canvas.drawLine(startX, startY, stopX, stopY, paint)

                paint.color = Color.RED
                canvas.drawCircle(startX, startY, 12f, paint)
            } else if (i > 0 && i < dataList.size - 1) {
                //Draw Middle Lines

                val startX = dataList[i].x
                val startY = height-(dataList[i].y+constraintDifference)
                val stopX = dataList[i + 1].x
                val stopY = height-(dataList[i + 1].y + constraintDifference)
                canvas.drawLine(startX, startY, stopX, stopY, paint)
            } else if (i == dataList.size - 1) {
                ////Draw Last Line -- END POINT

                val startX = dataList[i].x
                val startY = height-(dataList[i].y + constraintDifference)

                paint.color = Color.RED
                canvas.drawCircle(startX, startY, 12f, paint)
            }
        }

        //canvas.restore()

    }

    override fun setMinimumHeight(minHeight: Int) {
        super.setMinimumHeight(minHeight)
    }

    fun drawChart(dataList:List<ChartModel>){
        this.dataList = dataList

        upperLimit = dataList.maxOf { it.y }
        lowerLimit = dataList.minOf { it.y }

        invalidate()
    }



}