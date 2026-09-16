package com.example.settingsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity :
    AppCompatActivity() {

    private lateinit var preferences:
            SharedPreferences

    private val PREFS_NAME =
        "user_preferences"

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        preferences =
            getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        val etUsername =
            findViewById<EditText>(R.id.et_username)

        val swDarkMode =
            findViewById<Switch>(R.id.sw_dark_mode)

        val swRemember =
            findViewById<Switch>(R.id.sw_remember)

        val tvLaunchCount =
            findViewById<TextView>(R.id.tv_launch_count)

        val btnSave =
            findViewById<Button>(R.id.btn_save)

        val btnReset =
            findViewById<Button>(R.id.btn_reset)

        // Contador de aperturas
        val currentLaunches =
            preferences.getInt("launch_count", 0) + 1

        preferences
            .edit()
            .putInt("launch_count", currentLaunches)
            .apply()

        tvLaunchCount.text =
            "Veces abierta: $currentLaunches"

        // Recuperar configuraciones
        etUsername.setText(
            preferences.getString(
                "username",
                ""
            )
        )

        swDarkMode.isChecked =
            preferences.getBoolean(
                "dark_mode",
                false
            )

        swRemember.isChecked =
            preferences.getBoolean(
                "remember_session",
                false
            )

        // Guardar
        btnSave.setOnClickListener {

            val editor =
                preferences.edit()

            editor.putString(
                "username",
                etUsername.text.toString().trim()
            )

            editor.putBoolean(
                "dark_mode",
                swDarkMode.isChecked
            )

            editor.putBoolean(
                "remember_session",
                swRemember.isChecked
            )

            editor.apply()

            Toast.makeText(
                this,
                "Preferencias guardadas exitosamente",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Restablecer
        btnReset.setOnClickListener {

            preferences
                .edit()
                .clear()
                .apply()

            etUsername.text.clear()

            swDarkMode.isChecked = false

            swRemember.isChecked = false

            tvLaunchCount.text =
                "Veces abierta: 0"

            Toast.makeText(
                this,
                "Datos eliminados",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}