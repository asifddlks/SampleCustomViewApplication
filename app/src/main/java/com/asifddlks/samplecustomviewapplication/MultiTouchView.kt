package com.asifddlks.samplecustomviewapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View.MeasureSpec
import android.view.MotionEvent
import android.view.View
import java.lang.Exception

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


    override fun onDraw(canvas: Canvas) {
        if (isTouch[0]) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.BLUE
            //canvas.drawCircle(x[0], y[0], 50f, paint)
            canvas.drawLine(x[0],0f,x[0],height.toFloat(),paint)
        }
        if (isTouch[1]) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10f
            paint.color = Color.RED
            //canvas.drawCircle(x[1], y[1], 50f, paint)
            canvas.drawLine(x[1],0f,x[1],height.toFloat(),paint)
        }
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
}