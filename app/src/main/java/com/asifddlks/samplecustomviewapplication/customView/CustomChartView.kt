package com.asifddlks.samplecustomviewapplication.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.asifddlks.samplecustomviewapplication.ChartModel
import com.asifddlks.samplecustomviewapplication.R
import kotlin.math.abs


//
// Created by Asif Ahmed on 23/5/22.
//
class CustomChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var paint:Paint = Paint()

    var dataList:List<ChartModel> = ArrayList()

    var upperLimit: Float = 0f
    var lowerLimit: Float = Float.MAX_VALUE

    var constraintDifference = 0f

    var heightRatio = 0f
    var widthRatio = 0f

    val lineStrokeWidth = 2f

    var isMultiTouch:Boolean = false

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
        paint.strokeWidth = 2f/heightRatio
        //canvas.drawLine(0f, height - (upperLimit + constraintDifference), width.toFloat(), height - (upperLimit + constraintDifference), paint)
        //canvas.drawLine(0f, height - (lowerLimit + constraintDifference), width.toFloat(), height - (lowerLimit + constraintDifference), paint)

        for (i in dataList.indices){
            paint.strokeWidth = lineStrokeWidth/heightRatio

            if (touchOneNearestPointIndex != null && touchTwoNearestPointIndex != null && inColorRange(touchOneNearestPointIndex,i)) {
                isMultiTouch = true
                paint.color = context.getColor(R.color.orange_level_1)

            }
            else if (touchOneNearestPointIndex != null && touchTwoNearestPointIndex == null  && inColorRange(touchOneNearestPointIndex,i)) {
                isMultiTouch = false
                paint.color = context.getColor(R.color.orange_level_1)
            }
            else if (touchOneNearestPointIndex == null && touchTwoNearestPointIndex != null && inColorRange(touchTwoNearestPointIndex,i)) {
                isMultiTouch = false
                paint.color = context.getColor(R.color.orange_level_1)
            }
            else{
                paint.color = context.getColor(R.color.orange_level_2)
            }

            if (i == 0) {
                //Draw First Line
                val startX = (i/heightRatio)*widthRatio
                val startY = height-(dataList[i].closePrice + constraintDifference)
                val stopX = ((i+1)/heightRatio)*widthRatio
                val stopY = height-(dataList[i + 1].closePrice + constraintDifference)

                canvas.drawLine(startX, startY.toFloat(), stopX, stopY.toFloat(), paint)

                //paint.color = Color.RED
                //canvas.drawCircle(startX, startY.toFloat(), 12f/heightRatio, paint)
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

                //paint.color = Color.RED
                //canvas.drawCircle(startX, startY.toFloat(), 12f/heightRatio, paint)
            }
        }

        Log.d(this@CustomChartView.javaClass.simpleName,"width: ${width}")
        Log.d(this@CustomChartView.javaClass.simpleName,"height: ${height}")
        Log.d(this@CustomChartView.javaClass.simpleName,"upperLimit: ${upperLimit}")
        Log.d(this@CustomChartView.javaClass.simpleName,"lowerLimit: ${lowerLimit}")
        Log.d(this@CustomChartView.javaClass.simpleName,"constraintDifference: ${constraintDifference}")
        Log.d(this@CustomChartView.javaClass.simpleName,"heightRatio: ${heightRatio}")
        Log.d(this@CustomChartView.javaClass.simpleName,"widthRatio: ${widthRatio}")

        Log.d(this@CustomChartView.javaClass.simpleName,"widthRatio: ${widthRatio}")

        canvas.restore()

    }

    override fun setMinimumHeight(minHeight: Int) {
        super.setMinimumHeight(minHeight)
    }

    fun drawChart(dataList:List<ChartModel>){
        this.dataList = dataList

        val TOP_PADDING_OFFSET_VALUE = 2f
        upperLimit = dataList.maxOf { it.closePrice }.toFloat() + TOP_PADDING_OFFSET_VALUE
        lowerLimit = dataList.minOf { it.closePrice }.toFloat()

        invalidate()
    }

    fun inColorRange(touchNearestPointIndex:Int?, currentDataListIndex:Int):Boolean{

        var preOffsetValue = 0
        var postOffsetValue = 0

        if(isMultiTouch){
            //Log.d(this@CustomChartView.javaClass.simpleName,"touchOneNearestPointIndex: ${touchOneNearestPointIndex}")
            //Log.d(this@CustomChartView.javaClass.simpleName,"touchTwoNearestPointIndex: ${touchTwoNearestPointIndex}")

            if((touchTwoNearestPointIndex ?: 0) - (touchOneNearestPointIndex ?: 0)>=0){
                preOffsetValue = 0
                postOffsetValue = abs((touchTwoNearestPointIndex ?: 0) - (touchOneNearestPointIndex ?: 0))
            }
            else{
                preOffsetValue = abs((touchTwoNearestPointIndex ?: 0) - (touchOneNearestPointIndex ?: 0))
                postOffsetValue = 0
            }
        }
        else{
            preOffsetValue = 10
            postOffsetValue = 10
        }

        return if(touchNearestPointIndex==null){ false } else (touchNearestPointIndex in currentDataListIndex-postOffsetValue..currentDataListIndex+preOffsetValue)

    }

    private var touchOneNearestPointIndex:Int? = null
    private var touchOneChartModel:ChartModel? = null

    private var touchTwoNearestPointIndex:Int? = null
    private var touchTwoChartModel:ChartModel? = null

    fun touchOne(nearestPointIndex: Int, chartModel: ChartModel) {
        this.touchOneNearestPointIndex = nearestPointIndex
        this.touchOneChartModel = chartModel

        invalidate()
    }

    fun touchOneRemoved(){
        touchOneNearestPointIndex = null
        touchOneChartModel = null

        invalidate()
    }
    fun touchTwo(nearestPointIndex: Int, chartModel: ChartModel){
        this.touchTwoNearestPointIndex = nearestPointIndex
        this.touchTwoChartModel = chartModel

        invalidate()
    }
    fun touchTwoRemoved(){
        touchTwoNearestPointIndex = null
        touchTwoChartModel = null

        invalidate()
    }


}