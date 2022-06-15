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


    override fun onDraw(canvas: Canvas) {
        if (isTouch[0]) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.BLUE
            canvas.drawLine(findNearestPoint(x[0],dataList).x,0f,findNearestPoint(x[0],dataList).x,height.toFloat(),paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.BLACK
            paint.textSize = 20f
            canvas.drawText("x: ${findNearestPoint(x[0],dataList).x} y: ${findNearestPoint(x[0],dataList).y}",findNearestPoint(x[0],dataList).x+10,height-findNearestPoint(x[0],dataList).y,paint)
        }
        if (isTouch[1]) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.RED
            canvas.drawLine(findNearestPoint(x[1],dataList).x,0f,findNearestPoint(x[1],dataList).x,height.toFloat(),paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.BLACK
            paint.textSize = 20f
            canvas.drawText("x: ${findNearestPoint(x[1],dataList).x} y: ${findNearestPoint(x[1],dataList).y}",findNearestPoint(x[1],dataList).x+10,height-findNearestPoint(x[1],dataList).y,paint)
        }
    }

    private fun findNearestPoint(touchBarValue: Float, dataList: List<ChartModel>): ChartModel {
        var nearestXValue = 0f
        var nearestYValue = 0f
        var distance = Float.MAX_VALUE
        for(data in dataList){
            if(distance > abs(data.x - touchBarValue)){
                distance = abs(data.x - touchBarValue)
                nearestXValue = data.x
                nearestYValue = data.y
            }
        }
        return ChartModel(nearestXValue,nearestYValue)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
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

    fun drawChart(dataList:List<ChartModel>){
        this.dataList = dataList
        invalidate()
    }
}