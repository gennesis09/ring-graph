package com.example.testapplication

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout

class RingViewLoader (context: Context, attrs: AttributeSet?): View(context, attrs) {

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var currentAngle = -.25f
    private var lineWidth : Float = 55f
    private var _animatedValue : Float = 0f

    private val areaToDraw: RectF = RectF()
    private val marginGraph: Float = 50f

    private lateinit var paintBiggerQW: Paint

    init {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        initPainters()
        ValueAnimator.ofFloat(-1f, 0f).apply {
            duration = 5000
            interpolator = LinearInterpolator()
            addUpdateListener {
                _animatedValue = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        resetDrawArea()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
        resetDrawArea()
    }

    private fun resetDrawArea() {
        val diameter = kotlin.math.min(viewWidth.toFloat(), viewHeight.toFloat())
        lineWidth = diameter / 15f
        areaToDraw.set(marginGraph, marginGraph, diameter - marginGraph, diameter - marginGraph)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(areaToDraw, -.25f * 360f, _animatedValue * 360f, false, paintBiggerQW)
    }

    private fun initPainters() {
        paintBiggerQW = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.WHITE
            strokeWidth = lineWidth
        }
    }
}