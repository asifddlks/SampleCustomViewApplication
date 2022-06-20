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

    var heightRatio = 0f
    var widthRatio = 0f

    val lineStrokeWidth = 2f

    constructor(context: Context, attrs: AttributeSet):super(context,attrs){
        //paint.color = Color.BLUE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        heightRatio = (parentHeight.toFloat()/(upperLimit-lowerLimit))
        widthRatio = (parentWidth.toFloat()/(dataList.size-1).toFloat())
        //widthRatio = 5.5f
        setMeasuredDimension(parentWidth,parentHeight)
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawColor(Color.BLACK)

        constraintDifference = height - upperLimit
        //constraintDifference = 0f

        canvas.save()
        //move canvas to lower limit
        //canvas.translate(0f,lowerLimit+constraintDifference)

        //scale canvas respect to upper & lower limit
        canvas.scale(heightRatio,heightRatio)

        paint.color = Color.BLUE
        paint.strokeWidth = 20f/heightRatio
        canvas.drawLine(0f, height - (upperLimit + constraintDifference), width.toFloat(), height - (upperLimit + constraintDifference), paint)
        canvas.drawLine(0f, height - (lowerLimit + constraintDifference), width.toFloat(), height - (lowerLimit + constraintDifference), paint)

        for (i in dataList.indices){
            paint.color = Color.GREEN
            paint.strokeWidth = lineStrokeWidth/heightRatio

            if (i == 0) {
                //Draw First Line
                val startX = (i/heightRatio)*widthRatio
                val startY = height-(dataList[i].closePrice + constraintDifference)
                val stopX = ((i+1)/heightRatio)*widthRatio
                val stopY = height-(dataList[i + 1].closePrice + constraintDifference)

                canvas.drawLine(startX, startY.toFloat(), stopX, stopY.toFloat(), paint)

                paint.color = Color.RED
                canvas.drawCircle(startX, startY.toFloat(), 12f/heightRatio, paint)
            } else if (i > 0 && i < dataList.size - 1) {
                //Draw Middle Lines

                val startX = (i/heightRatio)*widthRatio
                val startY = height-(dataList[i].closePrice+constraintDifference)
                val stopX = ((i+1)/heightRatio)*widthRatio
                val stopY = height-(dataList[i + 1].closePrice + constraintDifference)
                canvas.drawLine(startX, startY.toFloat(), stopX, stopY.toFloat(), paint)
            } else if (i == dataList.size - 1) {
                ////Draw Last Line -- END POINT

                val startX = (i/heightRatio)*widthRatio
                val startY = height-(dataList[i].closePrice + constraintDifference)

                paint.color = Color.RED
                canvas.drawCircle(startX, startY.toFloat(), 12f/heightRatio, paint)
            }
        }

        canvas.restore()

    }

    override fun setMinimumHeight(minHeight: Int) {
        super.setMinimumHeight(minHeight)
    }

    fun drawChart(dataList:List<ChartModel>){
        this.dataList = dataList

        upperLimit = dataList.maxOf { it.closePrice }.toFloat()
        lowerLimit = dataList.minOf { it.closePrice }.toFloat()

        invalidate()
    }

    fun setTouchInterface(){

    }



}