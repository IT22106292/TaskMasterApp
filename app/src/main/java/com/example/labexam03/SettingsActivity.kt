package com.example.labexam03

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check and apply saved theme
        val sharedPreferences: SharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("darkMode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(R.layout.activity_settings)

        val switchTheme = findViewById<Switch>(R.id.switchTheme)
        val imageViewToggleTheme = findViewById<ImageView>(R.id.imageViewToggleTheme)

        switchTheme.isChecked = isDarkMode
        updateDrawable(isDarkMode, imageViewToggleTheme)

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("darkMode", true).apply()
                imageViewToggleTheme.setImageResource(R.drawable.moon)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("darkMode", false).apply()
                imageViewToggleTheme.setImageResource(R.drawable.sun)
            }
        }

        val buttonBackToMain = findViewById<ImageButton>(R.id.buttonBackToMain)
        buttonBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDrawable(isDarkMode: Boolean, imageView: ImageView) {
        val drawableId = if (isDarkMode) R.drawable.moon else R.drawable.sun
        imageView.setImageResource(drawableId)
    }
}
