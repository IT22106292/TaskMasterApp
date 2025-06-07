package com.example.labexam03

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class StopwatchActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private var isRunning = false
    private var service: StopwatchService? = null
    private var isBound = false
    private lateinit var handler: Handler

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder) {
            val stopwatchBinder = binder as StopwatchService.StopwatchBinder
            service = stopwatchBinder.getService()
            updateChronometer()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        chronometer = findViewById(R.id.chronometer)
        val buttonStart: ImageButton = findViewById(R.id.buttonStart)
        val buttonStop: ImageButton = findViewById(R.id.buttonStop)
        val buttonReset: ImageButton = findViewById(R.id.buttonReset)
        val buttonBackToMain: ImageButton = findViewById(R.id.buttonBackToMain)

        handler = Handler()

        buttonStart.setOnClickListener {
            startStopwatchService("START")
            isRunning = true
        }

        buttonStop.setOnClickListener {
            startStopwatchService("STOP")
            isRunning = false
        }

        buttonReset.setOnClickListener {
            startStopwatchService("RESET")
            isRunning = false
            chronometer.base = SystemClock.elapsedRealtime()
        }

        buttonBackToMain.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        bindService(Intent(this, StopwatchService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
        isBound = true
        updateChronometer()
    }

    override fun onPause() {
        super.onPause()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }

    private fun updateChronometer() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                service?.let {
                    val elapsedTime = it.getElapsedTime()
                    chronometer.base = SystemClock.elapsedRealtime() - elapsedTime
                    if (isRunning) {
                        chronometer.start()
                    } else {
                        chronometer.stop()
                    }
                }
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    private fun startStopwatchService(action: String) {
        val intent = Intent(this, StopwatchService::class.java)
        intent.action = action
        startService(intent)
    }
}
