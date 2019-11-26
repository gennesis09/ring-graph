package com.example.testapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var isActive = false
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//            .025f,.025f,.025f,
//            .005f,.005f,.005f,.005f,.002f))
//        button = findViewById(R.id.button)
//        findViewById<CardView>(R.id.card).setOnClickListener {
//            TransitionManager.beginDelayedTransition(root, TransitionSet().apply { addTransition(AutoTransition().apply { duration = 5000 }) })
//            if (isActive) root.animation = AnimationUtils.loadAnimation(this, R.anim.pay_invest_zoom_out)
//            else root.animation = AnimationUtils.loadAnimation(this, R.anim.pay_invest_zoom_in)
//            isActive = !isActive
//        }
    }

    override fun onResume() {
        super.onResume()
        val root = findViewById<RingGraphView>(R.id.ringGraphView)
        root.setData(listOf(
            .5f,
            .26f,
            .02f,
            .02f,

            .02f,
            .02f,
            .02f,
            .02f,
            .02f,

            .01f,
            .01f,
            .01f,
            .01f,
            .01f,
            .01f,
            .01f,
            .01f,
            .01f,
            .006f,
            .001f
        ))
    }
}
