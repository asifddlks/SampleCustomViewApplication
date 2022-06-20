package com.asifddlks.samplecustomviewapplication.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.asifddlks.samplecustomviewapplication.ChartModel
import com.asifddlks.samplecustomviewapplication.TouchPointInteractor
import kotlin.math.abs

//
// Created by Asif Ahmed on 14/6/22.
//
class MultiTouchView @JvmOverloads constructor(context: Context,
                     attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    //In this test, handle maximum of 2 pointer
    val MAX_POINT_COUNT = 2
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var x = FloatArray(MAX_POINT_COUNT)
    var y = FloatArray(MAX_POINT_COUNT)
    var isTouch = BooleanArray(MAX_POINT_COUNT)

    var dataList:List<ChartModel> = ArrayList()
    lateinit var touchPointInteractor:TouchPointInteractor

    var upperLimit: Float = 0f
    var lowerLimit: Float = Float.MAX_VALUE

    var constraintDifference = 0f

    var heightRatio = 0f
    var widthRatio = 0f

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

        constraintDifference = height - upperLimit

        Log.d(this@MultiTouchView.javaClass.simpleName,"width: ${width}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"height: ${height}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"upperLimit: ${upperLimit}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"lowerLimit: ${lowerLimit}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"constraintDifference: ${constraintDifference}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"heightRatio: ${heightRatio}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"widthRatio: ${widthRatio}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"findNearestPoint(x[0],dataList).x: ${findNearestPoint(x[0],dataList).time}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"findNearestPoint(x[0],dataList).y: ${findNearestPoint(x[0],dataList).closePrice}")
        Log.d(this@MultiTouchView.javaClass.simpleName,"x[0]: ${x[0]} || y[0]: ${y[0]}")

        if (isTouch[0]) {
            val startX = findNearestPointIndex(x[0],dataList)*widthRatio
            val startY = 0f
            val stopX = findNearestPointIndex(x[0],dataList)*widthRatio
            val stopY = height.toFloat()

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.BLUE
            canvas.drawLine(startX,startY,stopX,stopY,paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.WHITE
            paint.textSize = 20f

            val drawTextPositionX = startX+10
            val drawTextPositionY = height - ((findNearestPoint(x[0],dataList).closePrice-lowerLimit)*heightRatio)
            canvas.drawText("x: ${findNearestPoint(x[0],dataList).time} y: ${findNearestPoint(x[0],dataList).closePrice}",drawTextPositionX,drawTextPositionY.toFloat(),paint)

            touchPointInteractor.touchOne(findNearestPointIndex(x[0],dataList),ChartModel(findNearestPoint(x[0],dataList).time,findNearestPoint(x[0],dataList).closePrice))
        }
        if (isTouch[1]) {
            val startX = findNearestPointIndex(x[1],dataList)*widthRatio
            val startY = 0f
            val stopX = findNearestPointIndex(x[1],dataList)*widthRatio
            val stopY = height.toFloat()

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.RED
            canvas.drawLine(startX,startY,stopX,stopY,paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.WHITE
            paint.textSize = 20f
            val drawTextPositionX = startX+10
            val drawTextPositionY = height - ((findNearestPoint(x[1],dataList).closePrice-lowerLimit)*heightRatio)
            canvas.drawText("x: ${findNearestPoint(x[1],dataList).time} y: ${findNearestPoint(x[1],dataList).closePrice}",drawTextPositionX,drawTextPositionY.toFloat(),paint)

            touchPointInteractor.touchTwo(findNearestPointIndex(x[1],dataList),ChartModel(findNearestPoint(x[1],dataList).time,findNearestPoint(x[1],dataList).closePrice))
        }
    }

    private fun findNearestPoint(touchBarValue: Float, dataList: List<ChartModel>): ChartModel {
        var nearestXValue = ""
        var nearestYValue = 0.0
        var distance = Float.MAX_VALUE
        for(i in dataList.indices){
            if(distance > abs(i - touchBarValue/widthRatio)){
                distance = abs(i - touchBarValue/widthRatio)
                nearestXValue = dataList[i].time
                nearestYValue = dataList[i].closePrice

                //Log.d(this@MultiTouchView.javaClass.simpleName,"nearestXValue: ${nearestXValue} || nearestYValue: ${nearestYValue}")

            }
        }
        return ChartModel(nearestXValue,nearestYValue)
    }

    private fun findNearestPointIndex(touchBarValue: Float, dataList: List<ChartModel>): Int {
        var nearestXValue = ""
        var nearestYValue = 0.0
        var index = 0
        var distance = Float.MAX_VALUE
        for(i in dataList.indices){
            if(distance > abs(i - touchBarValue/widthRatio)){
                distance = abs(i - touchBarValue/widthRatio)
                nearestXValue = dataList[i].time
                nearestYValue = dataList[i].closePrice
                index = i

                //Log.d(this@MultiTouchView.javaClass.simpleName,"nearestXValue: ${nearestXValue} || nearestYValue: ${nearestYValue}")

            }
        }
        return index
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {

        val pointerIndex = (motionEvent.action and MotionEvent.ACTION_POINTER_ID_MASK shr MotionEvent.ACTION_POINTER_ID_SHIFT)
        val pointerId = motionEvent.getPointerId(pointerIndex)
        val action = motionEvent.action and MotionEvent.ACTION_MASK
        val pointCnt = motionEvent.pointerCount
        Log.d(this@MultiTouchView.javaClass.simpleName,"pointCnt: $pointCnt")

        try {
            if (pointCnt <= MAX_POINT_COUNT) {
                if (pointerIndex <= MAX_POINT_COUNT - 1) {
                    for (i in 0 until pointCnt) {
                        val id = motionEvent.getPointerId(i)
                        x[id] = motionEvent.getX(i)
                        y[id] = motionEvent.getY(i)
                    }
                    when (action) {
                        MotionEvent.ACTION_DOWN -> isTouch[pointerId] = true
                        MotionEvent.ACTION_POINTER_DOWN -> isTouch[pointerId] = true
                        MotionEvent.ACTION_MOVE -> isTouch[pointerId] = true
                        MotionEvent.ACTION_UP -> isTouch[pointerId] = false
                        MotionEvent.ACTION_POINTER_UP -> isTouch[pointerId] = false
                        MotionEvent.ACTION_CANCEL -> isTouch[pointerId] = false
                        else -> isTouch[pointerId] = false
                    }
                }
            }
            invalidate()

        }catch (e:Exception){
            Log.e(this@MultiTouchView.javaClass.simpleName,e.toString())
        }

        return true
    }

    fun drawChart(dataList:List<ChartModel>, touchPointInteractor:TouchPointInteractor){
        this.dataList = dataList
        this.touchPointInteractor = touchPointInteractor

        upperLimit = dataList.maxOf { it.closePrice }.toFloat()
        lowerLimit = dataList.minOf { it.closePrice }.toFloat()

        invalidate()
    }
}