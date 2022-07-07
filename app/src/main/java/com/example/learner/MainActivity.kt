package com.example.learner

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT =15

class MainActivity : AppCompatActivity() {
    /*Lines 19-28 we initialize the componenets from activity_main.xml */
    private lateinit var baseAmount: EditText
    private lateinit var tipFactor: SeekBar
    private lateinit var tipPercent: TextView
    private lateinit var tipPrice: TextView
    private lateinit var totalPrice: TextView
    private lateinit var peopleCount: TextView
    private lateinit var peopleCounter: EditText
    private lateinit var yourTotal: TextView
    private lateinit var finalTotalDisplay: TextView
    private lateinit var tipDescription: TextView
    /*onCreate we are setting up the application that the user will see */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*Here in lines 35-44 we are settting up the componenets with the findViewById feature */
        baseAmount = findViewById(R.id.baseAmount)
        tipPrice = findViewById(R.id.tipPrice)
        tipPercent = findViewById(R.id.tipPercent)
        tipFactor = findViewById(R.id.tipFactor)
        totalPrice = findViewById((R.id.totalPrice))
        peopleCount = findViewById(R.id.peopleCount)
        peopleCounter = findViewById(R.id.peopleCounter)
        yourTotal = findViewById(R.id.yourTotal)
        finalTotalDisplay = findViewById(R.id.finalTotalDisplay)
        tipDescription = findViewById(R.id.tipDescription)

        tipFactor.progress = INITIAL_TIP_PERCENT
        tipPercent.text = "$INITIAL_TIP_PERCENT%"
        updateTipDescrition(INITIAL_TIP_PERCENT)
        /*Here we are setting up the seekBar using a listener, which makes it so everytime that the seekbar changes other features change too */
        tipFactor.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {

                Log.i(TAG, "onProgressChanged $progress%")

                tipPercent.text = "$progress%"
                computeTipAndTotal()
                updateTipDescrition(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        /*The baseAmount listener works so that whenever the baseAmount is changed the other features change too */
        baseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }
        })
        /*The yourTotal listener has a bug that makes it so it will only change when the other components change */
        yourTotal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
            }
        })
    }
    /* The updateTipDescription function changes the tipDescription color and wording based off of the tipFactor*/
    private fun updateTipDescrition(tipPercent: Int) {
        val tipDescrip = when(tipPercent) {
            in 0..9 -> "Bad"
            in 10..14-> "Okay"
            in 15..19 -> "good"
            in 20..24 -> "great"
            else -> "exceptional"

        }
        tipDescription.text = tipDescrip

        val color =ArgbEvaluator().evaluate(

            tipPercent.toFloat()/ tipFactor.max,
            ContextCompat.getColor(this,R.color.red),
            ContextCompat.getColor(this,R.color.green)

        ) as Int

        tipDescription.setTextColor(color)

    }
            /*The computeTipAndTotal function updates the tipPrice, totalPrice and yourTotal based off of the other components */
            private fun computeTipAndTotal() {

                if (baseAmount.text.isEmpty()) {
                    tipPrice.text = " "
                    totalPrice.text = " "
                    yourTotal.text = " "
                    return
                }


                val base = baseAmount.text.toString().toDouble()
                val tipPer = tipFactor.progress
                val people = peopleCounter.text.toString().toDouble()

                val tip = base * tipPer / 100
                val total = base + tip
                val yourTot = total/people

                tipPrice.text = "%.2f".format(tip)
                totalPrice.text = "%.2f".format(total)
                yourTotal.text = "%.2f".format(yourTot)

            }






}
