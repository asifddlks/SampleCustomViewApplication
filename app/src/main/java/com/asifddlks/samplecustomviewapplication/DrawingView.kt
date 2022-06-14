package com.asifddlks.samplecustomviewapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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

    ///
    val MAX_POINT_COUNT: Int = 2

    var pointerAction = arrayOfNulls<String>(MAX_POINT_COUNT)
    var x = FloatArray(MAX_POINT_COUNT)
    var y = FloatArray(MAX_POINT_COUNT)
    var isMultiTouch = false
    ///

    private val paintOne: Paint = Paint()
    private val paintTwo: Paint = Paint()
    private val mPath: Path = Path()
    var canvasHeight:Int = 0
    var xCoordinateFromEvent_One = 0f
    var xCoordinateFromEvent_Two = 0f
    private var isFinished = false

    init
    {
        paintOne.apply {
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 10f
        }
        paintTwo.apply {
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
            paintOne.color = Color.TRANSPARENT
            paintOne.strokeWidth = 0f

            paintTwo.color = Color.TRANSPARENT
            paintTwo.strokeWidth = 0f
        }
        else{
            paintOne.color = Color.GRAY
            paintOne.strokeWidth = 10f

            paintTwo.color = Color.GRAY
            paintTwo.strokeWidth = 10f
        }

        if(!isMultiTouch){
            paintTwo.color = Color.TRANSPARENT
            paintTwo.strokeWidth = 10f
        }

        canvas.drawLine(xCoordinateFromEvent_One,0f,xCoordinateFromEvent_One,canvas.height.toFloat(),paintOne)
        canvas.drawLine(xCoordinateFromEvent_Two,0f,xCoordinateFromEvent_Two,canvas.height.toFloat(),paintTwo)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        val pointerIndex: Int = (event.action and MotionEvent.ACTION_POINTER_ID_MASK shr MotionEvent.ACTION_POINTER_ID_SHIFT)
        val pointerId: Int = event.getPointerId(pointerIndex)
        val action: Int = event.action and MotionEvent.ACTION_MASK
        val pointCount: Int = event.pointerCount

        Log.d(this@DrawingView.javaClass.simpleName,"pointerIndex: $pointerIndex")
        Log.d(this@DrawingView.javaClass.simpleName,"pointCount: $pointCount")
        Log.d(this@DrawingView.javaClass.simpleName,"pointerId: $pointerId")
        Log.d(this@DrawingView.javaClass.simpleName,"point event.getPointerId(0): ${event.getPointerId(0)}")

        isMultiTouch = pointCount > 1

        if (pointCount <= MAX_POINT_COUNT) {
            for (i in 0 until pointCount) {
                val id: Int = event.getPointerId(i)
                x[id] = event.getX(i)
                y[id] = event.getY(i)
            }

            when(action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d(this@DrawingView.javaClass.simpleName,"MotionEvent.ACTION_DOWN")
                    //mPath.moveTo(event.x, event.y)

                    //xCoordinateFromEvent_One = event.x
                    xCoordinateFromEvent_One = x[0]
                    xCoordinateFromEvent_Two = x[1]
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d(this@DrawingView.javaClass.simpleName,"MotionEvent.ACTION_MOVE")
                    //mPath.moveTo(event.x, event.y)
                    //mPath.lineTo(event.x, event.y)
                    isFinished =false
                    xCoordinateFromEvent_One = x[0]
                    xCoordinateFromEvent_Two = x[1]
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    Log.d(this@DrawingView.javaClass.simpleName,"MotionEvent.ACTION_UP point event.getPointerId(0): ${event.getPointerId(0)}")
                    Log.d(this@DrawingView.javaClass.simpleName,"MotionEvent.ACTION_UP")
                    //mPath.moveTo(event.x, event.y)
                    //mPath.lineTo(event.x, event.y)
                    isFinished = true
                    xCoordinateFromEvent_One = x[0]
                    xCoordinateFromEvent_Two = x[1]
                    invalidate()
                }
            }
        }


        return true
    }
}