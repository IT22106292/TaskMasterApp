package com.example.labexam03

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock

class StopwatchService : Service() {

    private var startTime = 0L
    private var elapsedTime = 0L
    private var isRunning = false
    private val binder = StopwatchBinder()

    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START" -> startStopwatch()
            "STOP" -> stopStopwatch()
            "RESET" -> resetStopwatch()
        }
        return START_STICKY
    }

    private fun startStopwatch() {
        if (!isRunning) {
            startTime = SystemClock.elapsedRealtime() - elapsedTime
            isRunning = true
        }
    }

    private fun stopStopwatch() {
        if (isRunning) {
            elapsedTime = SystemClock.elapsedRealtime() - startTime
            isRunning = false
        }
    }

    private fun resetStopwatch() {
        elapsedTime = 0L
        stopStopwatch()
    }

    fun getElapsedTime(): Long {
        return if (isRunning) {
            SystemClock.elapsedRealtime() - startTime
        } else {
            elapsedTime
        }
    }
}
