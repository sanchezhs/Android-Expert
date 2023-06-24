package com.example.myapplication.imccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.imccalculator.ImcCalculatorActivity.Companion.IMC_KEY

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvResult:TextView
    private lateinit var tvIMC:TextView
    private lateinit var tvDescription:TextView
    private lateinit var btnRecalculate:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)
        val result = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        initComponents()
        initUI(result)
        initListeners()
    }

    private fun initListeners() {
        btnRecalculate.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initUI(result: Double) {
        val roundedResult = String.format("%.2f", result)
        tvIMC.text = roundedResult
        when(result) {
            in 0.00 .. 18.50 -> {
                tvResult.text = getString(R.string.title_low_weight)
                tvDescription.text = getString(R.string.description_low_weight)
            }
            in 18.51 .. 24.99 -> {
                tvResult.text = getString(R.string.title_normal_weight)
                tvDescription.text = getString(R.string.description_normal_weight)
            }
            in 25.00 .. 29.99 -> {
                tvResult.text = getString(R.string.title_high_weight)
                tvDescription.text = getString(R.string.description_high_weight)
            }
            in 30.00 .. 99.00 -> {
                tvResult.text = getString(R.string.title_extreme_weight)
                tvDescription.text = getString(R.string.description_extreme_weight)
            }
            else -> {
                tvResult.text = getString(R.string.error)
                tvResult.text = getString(R.string.error)
                tvDescription.text = getString(R.string.error)
            }
        }
    }

    private fun initComponents() {
        tvResult = findViewById(R.id.tvResult)
        tvIMC = findViewById(R.id.tvIMC)
        tvDescription = findViewById(R.id.tvDescription)
        btnRecalculate = findViewById(R.id.btnReCalculate)
    }
}