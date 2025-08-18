package com.example.unitconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private lateinit var inputValue: EditText
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultView: TextView

    private val units = arrayOf("Meters", "Centimeters", "Inches", "Feet")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputValue = findViewById(R.id.inputValue)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultView = findViewById(R.id.resultView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter

        convertButton.setOnClickListener {
            convertUnits()
        }
    }

    private fun convertUnits() {
        val inputText = inputValue.text.toString()
        if (inputText.isEmpty()) {
            resultView.text = "Please enter a value"
            return
        }

        val valueToConvert = inputText.toDoubleOrNull()
        if (valueToConvert == null) {
            resultView.text = "Invalid input"
            return
        }

        val fromUnit = fromUnitSpinner.selectedItem.toString()
        val toUnit = toUnitSpinner.selectedItem.toString()

        val valueInMeters = convertToMeters(valueToConvert, fromUnit)
        val result = convertFromMeters(valueInMeters, toUnit)

        // Using BigDecimal for better precision
        val resultBigDecimal = BigDecimal(result).setScale(4, RoundingMode.HALF_UP)
        resultView.text = resultBigDecimal.toPlainString()
    }

    private fun convertToMeters(value: Double, fromUnit: String): Double {
        return when (fromUnit) {
            "Meters" -> value
            "Centimeters" -> value / 100.0
            "Inches" -> value * 0.0254
            "Feet" -> value * 0.3048
            else -> 0.0
        }
    }

    private fun convertFromMeters(value: Double, toUnit: String): Double {
        return when (toUnit) {
            "Meters" -> value
            "Centimeters" -> value * 100.0
            "Inches" -> value / 0.0254
            "Feet" -> value / 0.3048
            else -> 0.0
        }
    }
}
