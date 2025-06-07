package com.example.labexam03

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Splash screen duration (e.g., 3 seconds)
        val splashScreenDuration = 3000L // 3 seconds

        // Navigate to MainActivity after the splash screen duration
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close the SplashActivity so it's removed from the back stack
        }, splashScreenDuration)
    }
}
