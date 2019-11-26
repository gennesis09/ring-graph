package com.example.testapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class RingView (context: Context, attrs: AttributeSet?): View(context, attrs) {

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var currentAngle = -.25f
    private var space = .018f
    private var minimumWithCornerRadius = .02f
    private var minimumApplyDefaultSpace = .0008f
    private var lineWidth : Float = 50f

    private val areaToDraw: RectF = RectF()
    private val marginGraph: Float = 50f

    private lateinit var paintBiggerQW: Paint
    private lateinit var paintWithCornerRadius: Paint
    private lateinit var paintWithoutCornerRadius: Paint
    private lateinit var dataList: List<Float>

    init {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        initPainters()
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
//        areaToDraw.offsetTo(paddingLeft.toFloat(), paddingTop.toFloat())
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (dataList.isNotEmpty()) {
            drawDount(canvas)
        }
    }

    fun setData(list: List<Float>) {
        dataList = list
        invalidate()
    }

    private fun drawDount(canvas: Canvas?) {
        if (dataList.size == 1) {
//            paintPrimary?.color = Color.parseColor(mList[0].color)
            canvas?.drawArc(areaToDraw, currentAngle, 360f, false, paintWithoutCornerRadius)
            return
        }

        dataList.forEach {
            drawSlice(canvas, it)
        }
    }

    private fun drawSlice(canvas: Canvas?, slice: Float) {
        val sliceSpace = if (slice > minimumApplyDefaultSpace && slice != 1f) {
            space
        } else {
            .01f
        }
        val startAngle = if (slice < minimumWithCornerRadius && slice != 1f) {
            currentAngle
        } else {
            currentAngle + sliceSpace
        }

        val endAngle = if (slice < 0.02f) {
            slice - 0.003f
        }
        else {
            slice - (sliceSpace * 2)
        }

        val startPoint = calculateSweep(startAngle)
        val endPoint = calculateSweep(endAngle)
        if (slice > minimumWithCornerRadius) {
            canvas?.drawArc(areaToDraw, startPoint, endPoint, false, paintWithCornerRadius)
        }
        else {
            canvas?.drawArc(areaToDraw, startPoint, endPoint, false, paintBiggerQW)
        }

        currentAngle += slice
    }

    private fun calculateSweep(percent: Float) : Float = percent * 360

    private fun validateData(list: List<Float>): Boolean =
        (list.reduce { s, t -> s + t } == 1f)

    private fun initPainters() {
        paintWithCornerRadius = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = resources.getColor(R.color.colorAccent)
            strokeWidth = lineWidth
        }

        paintBiggerQW = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = resources.getColor(R.color.colorAccent)
            strokeWidth = lineWidth
        }

        paintWithoutCornerRadius = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = resources.getColor(R.color.colorPrimary)
            strokeWidth = lineWidth
        }
    }
}