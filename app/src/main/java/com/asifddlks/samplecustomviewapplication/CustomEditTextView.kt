package com.asifddlks.samplecustomviewapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText

//
// Created by Asif Ahmed on 23/5/22.
//
class CustomEditTextView:androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context, attrs: AttributeSet):super(context,attrs){
        //paint.color = Color.BLUE
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText("hello $text", type)


    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)



        super.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
    }



}