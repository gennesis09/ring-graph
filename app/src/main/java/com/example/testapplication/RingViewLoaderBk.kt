package com.example.testapplication

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class RingViewLoaderBk (context: Context, attrs: AttributeSet?): View(context, attrs) {

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var currentAngle = -.25f
    private var space = .018f
    private var minimumWithCornerRadius = .02f
    private var minimumApplyDefaultSpace = .003f
    private var lineWidth : Float = 50f
    private var _animatedValue : Float = 0f
    private var slice: Float = 0f
    private var actualSliceCount: Int = 0

    private val areaToDraw: RectF = RectF()
    private val marginGraph: Float = 50f

    private lateinit var paintBiggerQW: Paint
    private lateinit var paintWithCornerRadius: Paint
    private lateinit var paintWithoutCornerRadius: Paint
    private lateinit var actualSlice: RingGraphDataModel

    private val dataListToAnimate: MutableList<RingGraphDataModel> = mutableListOf()
    private val dataListAnimated: MutableList<RingGraphDataModel> = mutableListOf()
    private val valueAnimator = ValueAnimator()

    init {
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
//        dataListAnimated.forEach {
//            canvas?.drawArc(areaToDraw, it.startAngle, it.endAngle, false, it.paintSlice!!)
//        }

        canvas?.drawArc(areaToDraw, -90f, 360f, false, paintWithCornerRadius)
//        canvas?.drawArc(areaToDraw, calculateSweep(actualSlice.startAngle), calculateSweep(_animatedValue), false, actualSlice.paintSlice!!)
    }

    fun setData(list: List<Float>) {
        dataListToAnimate.addAll(list.map {
            mapData(it)
        })

//        actualSlice = dataListToAnimate[actualSliceCount].copy()
//        ValueAnimator.ofFloat(currentAngle, actualSlice.endAngle).apply {
//            duration = 5000
//            interpolator = LinearInterpolator()
//            addUpdateListener {
//                _animatedValue = it.animatedValue as Float
//                invalidate()
//            }
//            addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator?) {
//                    super.onAnimationEnd(animation)
//                    dataListAnimated.add(actualSlice.copy())
////                    actualSliceCount++
////                    actualSlice = dataListToAnimate[actualSliceCount].copy()
//                    invalidate()
//                }
//            })
//            start()
//        }
    }

    private fun drawSlice(canvas: Canvas?, slice: Float) {
        this.slice = slice
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

        valueAnimator.setFloatValues(startPoint, endPoint)
        valueAnimator.start()


        if (slice > minimumWithCornerRadius) {
            canvas?.drawArc(areaToDraw, startPoint, _animatedValue, false, paintWithCornerRadius)
        }
        else {
            canvas?.drawArc(areaToDraw, startPoint, _animatedValue, false, paintWithoutCornerRadius)
        }
    }

    private fun mapData(slice: Float): RingGraphDataModel {
        this.slice = slice
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

//        currentAngle += slice

        return if (slice > minimumWithCornerRadius) {
            RingGraphDataModel(startAngle, endAngle, paintWithCornerRadius)
        } else {
            RingGraphDataModel(startAngle, endAngle, paintWithoutCornerRadius)
        }
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
//            pathEffect = DashPathEffect(floatArrayOf(200f, 60f, 50f, 60f, .5f, 30f), 0f)
        }

        paintBiggerQW = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.WHITE
            strokeWidth = lineWidth
        }

        paintWithoutCornerRadius = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = resources.getColor(R.color.colorPrimary)
            strokeWidth = lineWidth
        }
    }

    private data class RingGraphDataModel(
        val startAngle: Float,
        val endAngle: Float,
        val paintSlice: Paint? = null
        )
}