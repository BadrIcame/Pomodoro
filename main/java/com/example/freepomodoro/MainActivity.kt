package com.example.freepomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    val START_TIME_IN_MILLIS : Long = 25 * 60 * 1000
    var remainingTime : Long = START_TIME_IN_MILLIS
    var timer: CountDownTimer? = null
    var isTimerRunning = false
    lateinit var pb: ProgressBar


    lateinit var titleTv : TextView
    lateinit var timerTv : TextView
    lateinit var startBtn: Button
    lateinit var resetTv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTv = findViewById(R.id.title_tv)
        timerTv = findViewById(R.id.timer_tv)
        startBtn = findViewById(R.id.start_btn)
        resetTv = findViewById(R.id.reset_tv)
        pb = findViewById(R.id.progressBar)

        startBtn.setOnClickListener {
            if (!isTimerRunning){ // !isTimerRunning <=> isTimerRunning == false
                startTimer()
                titleTv.text = resources.getText(R.string.keep_going)
            }

        }

        resetTv.setOnClickListener {
            resetTimer()

        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            override fun onTick(timeLeft: Long) {
                remainingTime = timeLeft
                updateTimerText()
                pb.progress = remainingTime.toDouble().div(START_TIME_IN_MILLIS.toDouble()).times(100).toInt()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Finish", Toast.LENGTH_SHORT).show()
                isTimerRunning = false
            }

        }.start()

        isTimerRunning = true
    }

    private fun resetTimer(){
        timer?.cancel()
        remainingTime = START_TIME_IN_MILLIS
        updateTimerText()
        titleTv.text = resources.getText(R.string.take_pomodoro)
        isTimerRunning = false
        pb.progress = 100
    }

    private fun updateTimerText(){
        val minute = remainingTime.div(1000).div(60)
        val seconde = remainingTime.div(1000) % 60
        val formatedTime = String.format("%02d:%02d", minute, seconde)
        timerTv.text = formatedTime
    }
}

