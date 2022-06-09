package com.asifddlks.samplecustomviewapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


//
// Created by Asif Ahmed on 9/6/22.
//
class DrawingView @JvmOverloads constructor(context: Context,
                                             attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private val mPaint: Paint = Paint()
    private val mPath: Path = Path()
    var canvasHeight:Int = 0
    var xCoordinateFromEvent = 0f
    private var isFinished = false

    init
    {
        mPaint.apply {
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 10f
        }
    }

    // Called when the view should render its content.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawPath(mPath, mPaint)
        if(isFinished){
            mPaint.color = Color.TRANSPARENT
            mPaint.strokeWidth = 0f
        }
        else{
            mPaint.color = Color.GRAY
            mPaint.strokeWidth = 10f
        }

        canvas.drawLine(xCoordinateFromEvent,0f,xCoordinateFromEvent,canvas.height.toFloat(),mPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(this@DrawingView.javaClass.simpleName,"MotionEvent.ACTION_DOWN")
                //mPath.moveTo(event.x, event.y)

                xCoordinateFromEvent = event.x
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(this@DrawingView.javaClass.simpleName,"MotionEvent.ACTION_MOVE")
                //mPath.moveTo(event.x, event.y)
                //mPath.lineTo(event.x, event.y)
                isFinished =false
                xCoordinateFromEvent = event.x
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                Log.d(this@DrawingView.javaClass.simpleName,"MotionEvent.ACTION_UP")
                //mPath.moveTo(event.x, event.y)
                //mPath.lineTo(event.x, event.y)
                isFinished = true
                xCoordinateFromEvent = event.x
                invalidate()
            }
        }
        return true
    }
}