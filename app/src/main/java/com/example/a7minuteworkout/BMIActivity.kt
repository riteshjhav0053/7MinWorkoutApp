package com.example.a7minuteworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a7minuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.etWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etHeight?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true
        if (binding?.etPound?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etUSMetricUnitHeightFeet?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etUSMetricUnitHeightInch?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }


    private fun makeVisibleMetricUnitsView() {

        val params = binding?.llDisplayBMIResult?.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = binding?.tilHeight?.id ?: View.NO_ID
        binding?.llDisplayBMIResult?.layoutParams = params

        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilWeight?.visibility = View.VISIBLE
        binding?.tilHeight?.visibility = View.VISIBLE
        binding?.tilPound?.visibility = View.GONE
        binding?.tilMetricUnitHeightFeet?.visibility = View.GONE
        binding?.tilMetricUnitHeightInch?.visibility = View.GONE
        binding?.llDisplayBMIResult?.visibility = View.GONE
//        binding?.tilWeight?.hint= "Weight (in Kg)"
        binding?.etUSMetricUnitHeightFeet?.text?.clear()
        binding?.etUSMetricUnitHeightInch?.text?.clear()
        binding?.etPound?.text?.clear()

    }
    private fun makeVisibleUsUnitsView() {

        val params = binding?.llDisplayBMIResult?.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = binding?.tilMetricUnitHeightInch?.id ?: View.NO_ID
        binding?.llDisplayBMIResult?.layoutParams = params

        currentVisibleView = US_UNITS_VIEW
        binding?.tilWeight?.visibility = View.GONE
        binding?.tilHeight?.visibility = View.GONE
        binding?.tilPound?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeightInch?.visibility = View.VISIBLE
        binding?.llDisplayBMIResult?.visibility = View.GONE
//        binding?.tilPound?.hint = "Weight (in pounds)"
        binding?.etWeight?.text?.clear()
        binding?.etHeight?.text?.clear()

    }

    private fun calculateMetricUnits(){
        val heightValue: Float = binding?.etHeight?.text.toString().toFloat() / 100
        val weightValue: Float = binding?.etWeight?.text.toString().toFloat()
        val bmi = weightValue / (heightValue * heightValue)
        displayBMIResult(bmi)
    }

    private fun calculateUsUnits(){

        val heightFeetValue: String = binding?.etUSMetricUnitHeightFeet?.text.toString()
        val heightInchValue: String = binding?.etUSMetricUnitHeightInch?.text.toString()
        val weightValue: Float = binding?.etPound?.text.toString().toFloat()
        if (heightFeetValue.isNotEmpty() && heightInchValue.isNotEmpty() && weightValue.toString().isNotEmpty()) {
            val heightValue = heightInchValue.toFloat() + heightFeetValue.toFloat() * 12
            val bmi = 703 * (weightValue.toFloat() / (heightValue * heightValue))
            displayBMIResult(bmi)
        } else {
            Toast.makeText(this, "Please enter valid US Unit values.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            // For metric units
            if (validateMetricUnits()) {
                calculateMetricUnits()
            } else {
                Toast.makeText(
                    this,
                    "Please enter valid Metric values.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // For US units
            if (validateUsUnits()) {
                calculateUsUnits()
            } else {
                Toast.makeText(
                    this,
                    "Please enter valid US values.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun displayBMIResult(bmi: Float) {

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE

        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }
        else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }
        else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }


        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }
}


