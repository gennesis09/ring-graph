package com.example.testapplication

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class RingGraphView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : ViewGroup(context, attributeSet) {

    private var ringView : RingView
    private var ringViewLoader: RingViewLoader

    init {
        ringView = RingView(context, attributeSet)
        ringViewLoader = RingViewLoader(context, attributeSet)
        addView(ringView)
        addView(ringViewLoader)
    }

    fun setData(list: List<Float>) {
        ringView.setData(list)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        ringView.layout(l, t, r, b)
        ringViewLoader.layout(l, t, r, b)
    }
}