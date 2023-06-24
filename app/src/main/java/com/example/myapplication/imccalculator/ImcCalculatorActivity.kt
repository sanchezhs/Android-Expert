package com.example.myapplication.imccalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class ImcCalculatorActivity : AppCompatActivity() {

    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currentWeight: Int = 60
    private var currentAge: Int = 25
    private var currentHeight: Int = 120

    private lateinit var viewMale:CardView
    private lateinit var viewFemale:CardView
    private lateinit var tvHeight:TextView
    private lateinit var tvWeight:TextView
    private lateinit var tvAge: TextView
    private lateinit var rsHeight:RangeSlider
    private lateinit var btnSubtract:FloatingActionButton
    private lateinit var btnPlus:FloatingActionButton
    private lateinit var btnSubtractAge: FloatingActionButton
    private lateinit var btnAddAge: FloatingActionButton
    private lateinit var btnCalculate: Button

    companion object {
        const val IMC_KEY = "IMC_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }



    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        tvWeight = findViewById(R.id.tvWeight)
        tvAge = findViewById(R.id.tvAge)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubtract = findViewById(R.id.btnSubtractWeight)
        btnPlus = findViewById(R.id.btnAddWeight)
        btnSubtractAge = findViewById(R.id.btnSubtractAge)
        btnAddAge = findViewById(R.id.btnAddAge)
        btnCalculate = findViewById(R.id.btnCalculate)
    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        rsHeight.addOnChangeListener {_, value, _ ->
            currentHeight = value.toInt()
            tvHeight.text = "${currentHeight.toString()} cm"
        }
        btnPlus.setOnClickListener {
            currentWeight++
            setWeight()
        }
        btnSubtract.setOnClickListener{
            currentWeight--
            setWeight()
        }
        btnSubtractAge.setOnClickListener {
            currentAge--
            setAge()
        }
        btnAddAge.setOnClickListener {
            currentAge++
            setAge()
        }
        btnCalculate.setOnClickListener {
            navigateToResult(calculateIMC())
        }
    }

    private fun navigateToResult(result: Double) {
        val intent = Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        startActivity(intent)
    }

    private fun calculateIMC(): Double {
        return(currentWeight / (currentHeight.toDouble()/100 * currentHeight.toDouble()/100))
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    private fun setAge() {
        tvAge.text = currentAge.toString()
    }

    private fun changeGender() {
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent:Boolean): Int {
        val ref = if (isSelectedComponent) {
            R.color.background_component_selected
        } else {
            R.color.background_component
        }

        return ContextCompat.getColor(this, ref)
    }

}