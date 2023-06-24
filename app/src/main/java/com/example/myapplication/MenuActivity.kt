package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.imccalculator.ImcCalculatorActivity
import com.example.myapplication.settings.SettingsActivity
import com.example.myapplication.superheroapp.SuperHeroListActivity
import com.example.myapplication.todoapp.TodoActivity
import com.example.myapplication.videoapp.FirstAppActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val btnSaludarApp = findViewById<Button>(R.id.btnSaludarApp)
        val btnIMCApp = findViewById<Button>(R.id.btnIMCApp)
        val btnTODO = findViewById<Button>(R.id.btnTODO)
        val btnSuperhero = findViewById<Button>(R.id.btnSuperhero)
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        btnIMCApp.setOnClickListener { navigateToIMCApp() }
        btnSaludarApp.setOnClickListener { navigateToSaludarApp() }
        btnTODO.setOnClickListener { navigateToTODOApp() }
        btnSuperhero.setOnClickListener { navigateToSuperheroApp() }
        btnSettings.setOnClickListener { navigateToSettings() }
    }

    private fun navigateToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun navigateToTODOApp() {
        val intent = Intent(this, TodoActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToIMCApp() {
        val intent = Intent(this, ImcCalculatorActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSaludarApp() {
        val intent = Intent(this, FirstAppActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSuperheroApp() {
        val intent = Intent(this, SuperHeroListActivity::class.java)
        startActivity(intent)
    }
}