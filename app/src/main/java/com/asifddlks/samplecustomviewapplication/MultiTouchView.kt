package com.asifddlks.samplecustomviewapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

//
// Created by Asif Ahmed on 14/6/22.
//
class MultiTouchView @JvmOverloads constructor(context: Context,
                     attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    //In this test, handle maximum of 2 pointer
    val MAX_POINT_CNT = 2
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var x = FloatArray(MAX_POINT_CNT)
    var y = FloatArray(MAX_POINT_CNT)
    var isTouch = BooleanArray(MAX_POINT_CNT)

    var dataList:List<ChartModel> = ArrayList()


    override fun onDraw(canvas: Canvas) {
        if (isTouch[0]) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.BLUE
            //canvas.drawCircle(x[0], y[0], 50f, paint)
            canvas.drawLine(findNearestPoint(x[0],dataList),0f,findNearestPoint(x[0],dataList),height.toFloat(),paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.BLACK
            paint.textSize = 20f
            canvas.drawText("x: ${findNearestPoint(x[0],dataList)} y: ${y[0]}",findNearestPoint(x[0],dataList),y[0]-80f,paint)

        }
        if (isTouch[1]) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.RED
            //canvas.drawCircle(x[1], y[1], 50f, paint)


            canvas.drawLine(findNearestPoint(x[1],dataList),0f,findNearestPoint(x[1],dataList),height.toFloat(),paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.BLACK
            paint.textSize = 20f
            canvas.drawText("x: ${findNearestPoint(x[1],dataList)} y: ${y[1]}",findNearestPoint(x[1],dataList),y[1]-80f,paint)
        }
    }

    private fun findNearestPoint(touchBarValue: Float, dataList: List<ChartModel>): Float {
        var nearestValue = 0f
        var distance = Float.MAX_VALUE
        for(data in dataList){
            if(distance > abs(data.x - touchBarValue)){
                distance = abs(data.x - touchBarValue)
                nearestValue = data.x
            }
        }
        return nearestValue
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
            if (pointCnt <= MAX_POINT_CNT) {
                if (pointerIndex <= MAX_POINT_CNT - 1) {
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