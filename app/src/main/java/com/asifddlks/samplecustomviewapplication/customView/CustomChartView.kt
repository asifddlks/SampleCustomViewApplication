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

    constructor(context: Context, attrs: AttributeSet):super(context,attrs){
        //paint.color = Color.BLUE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(parentWidth,parentHeight)
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawColor(Color.WHITE)

        for (i in dataList.indices){
            paint.color = Color.MAGENTA
            paint.strokeWidth = 10f

            if (i == 0) {
                canvas.drawLine(
                    dataList[i].x,
                    height-dataList[i].y, dataList[i + 1].x, height-dataList[i + 1].y, paint
                )
                paint.color = Color.GREEN
                canvas.drawLine(
                    dataList[i].x,
                    height-dataList[i].y, dataList[i + 1].x, height-dataList[i + 1].y, paint
                )
                paint.color = Color.RED
                canvas.drawCircle(dataList[i].x, height-dataList[i].y, 12f, paint)
                paint.color = Color.GREEN
                canvas.drawCircle(dataList[i].x, height-dataList[i].y, 12f, paint)
            } else if (i > 0 && i < dataList.size - 1) {
                canvas.drawLine(
                    dataList[i].x,
                    height-dataList[i].y, dataList[i + 1].x, height-dataList[i + 1].y, paint
                )
                paint.color = Color.RED
                canvas.drawCircle(dataList[i].x, height-dataList[i].y, 12f, paint)
                paint.color = Color.GREEN
                canvas.drawLine(
                    dataList[i].x,
                    height-dataList[i].y, dataList[i + 1].x, height-dataList[i + 1].y, paint
                )
                paint.color = Color.GREEN
                canvas.drawCircle(dataList[i].x, height-dataList[i].y, 12f, paint)
            } else if (i == dataList.size - 1) {
                paint.color = Color.RED
                canvas.drawCircle(dataList[i].x, height-dataList[i].y, 12f, paint)
                paint.color = Color.GREEN
                canvas.drawCircle(dataList[i].x, height-dataList[i].y, 12f, paint)
            }
        }

        /*canvas.drawColor(Color.WHITE)
        paint.color = Color.GRAY
        paint.textSize = 50f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("HelloWorld", width.toFloat()/2, height.toFloat()-context.resources.displayMetrics.density*20, paint)
        val xStopPointsLine1 = floatArrayOf(0f, 200.1f, 450.5f, 650f, 850f)
        val yStopPointsLine1 = floatArrayOf(100f, 380f, 540f, 400f, 720f)
        val xStopPointsLine2 = floatArrayOf(20f, 170.1f, 350.5f, 480f, 650f)
        val yStopPointsLine2 = floatArrayOf(200f, 480f, 240f, 600f, 380f)

        if(isDraw){
            paint.color = Color.MAGENTA
            canvas.drawLine(0f, 0f, 400f, height.toFloat(), paint)
        }

        for (i in yStopPointsLine1.indices) {
            paint.color = Color.GRAY
            paint.strokeWidth = 8f
            if (i == 0) {
                canvas.drawLine(
                    xStopPointsLine1[i],
                    yStopPointsLine1[i], xStopPointsLine1[i + 1], yStopPointsLine1[i + 1], paint
                )
                paint.color = Color.GREEN
                canvas.drawLine(
                    xStopPointsLine2[i],
                    yStopPointsLine2[i], xStopPointsLine2[i + 1], yStopPointsLine2[i + 1], paint
                )
                paint.color = Color.RED
                canvas.drawCircle(xStopPointsLine1[i], yStopPointsLine1[i], 12f, paint)
                paint.color = Color.GREEN
                canvas.drawCircle(xStopPointsLine2[i], yStopPointsLine2[i], 12f, paint)
            } else if (i > 0 && i < yStopPointsLine1.size - 1) {
                canvas.drawLine(
                    xStopPointsLine1[i],
                    yStopPointsLine1[i], xStopPointsLine1[i + 1], yStopPointsLine1[i + 1], paint
                )
                paint.color = Color.RED
                canvas.drawCircle(xStopPointsLine1[i], yStopPointsLine1[i], 12f, paint)
                paint.color = Color.GREEN
                canvas.drawLine(
                    xStopPointsLine2[i],
                    yStopPointsLine2[i], xStopPointsLine2[i + 1], yStopPointsLine2[i + 1], paint
                )
                paint.color = Color.GREEN
                canvas.drawCircle(xStopPointsLine2[i], yStopPointsLine2[i], 12f, paint)
            } else if (i == yStopPointsLine1.size - 1) {
                paint.color = Color.RED
                canvas.drawCircle(xStopPointsLine1[i], yStopPointsLine1[i], 12f, paint)
                paint.color = Color.GREEN
                canvas.drawCircle(xStopPointsLine2[i], yStopPointsLine2[i], 12f, paint)
            }
        }*/


    }

    override fun setMinimumHeight(minHeight: Int) {
        super.setMinimumHeight(minHeight)
    }

    fun drawChart(dataList:List<ChartModel>){
        this.dataList = dataList
        invalidate()
    }

}