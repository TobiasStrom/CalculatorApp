package com.example.calculatorapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.evgenii.jsevaluator.JsEvaluator
import com.evgenii.jsevaluator.interfaces.JsCallback

const val HAS_RESULT = "has_result"
const val EXPRESSION = "expression"
const val LAST_IS_NOT_NUMBER = "last_is_not_number"

class CalculatorActivity : AppCompatActivity(), View.OnClickListener {

    //Todo Question: what is the significance of  "lateinit"?
    lateinit var jsEvaluator: JsEvaluator

    lateinit var textViewExpression: TextView
    lateinit var button0: Button
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var button4: Button
    lateinit var button5: Button
    lateinit var button6: Button
    lateinit var button7: Button
    lateinit var button8: Button
    lateinit var button9: Button
    lateinit var buttonMulit: Button
    lateinit var buttonMinus: Button
    lateinit var buttonPlus: Button
    lateinit var buttonClear: Button
    lateinit var buttonDel: Button
    lateinit var buttonEq: Button

    var expression: String = "0"
    var hasResult: Boolean = false
    var lastIsNotNumber: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        buttonEq = findViewById(R.id.buttonEq)
        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        buttonMulit = findViewById(R.id.buttonMulti)
        buttonMinus = findViewById(R.id.buttonMinus)
        buttonPlus = findViewById(R.id.buttonPlus)
        buttonClear = findViewById(R.id.buttonClear)
        buttonDel = findViewById(R.id.buttonDel)
        textViewExpression = findViewById<Button>(R.id.textView)


        expression = savedInstanceState?.getString(EXPRESSION) ?: "0"
        lastIsNotNumber = savedInstanceState?.getBoolean(LAST_IS_NOT_NUMBER) ?: false
        hasResult = savedInstanceState?.getBoolean(HAS_RESULT) ?: false

        textViewExpression.text = expression

        jsEvaluator = JsEvaluator(this)

        //Handling of click event for buttonEq

        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        buttonPlus.setOnClickListener(this)
        buttonMinus.setOnClickListener(this)
        buttonMulit.setOnClickListener(this)

        buttonClear.setOnClickListener { clear() }
        buttonDel.setOnClickListener { del() }
        buttonEq.setOnClickListener { evaluate() }
    }

    private fun clear() {
        textViewExpression.text = "0"
        expression = ""
    }

    private fun del() {
        var split = expression.split("").filter { it.isNotEmpty() }
        Log.d("Test", split.toString())
        if (split.isNotEmpty()) {
            split = split.dropLast(1)
        }
        if (split.size == 0) {
            expression = "0"
        } else {
            expression = split.joinToString("")
        }
        textViewExpression.text = expression
    }

    private fun evaluate() {
        if (textViewExpression.text.isNotBlank() && !lastIsNotNumber) {
            jsEvaluator.evaluate(textViewExpression.text as String, object : JsCallback {
                override fun onResult(result: String) {
                    textViewExpression.text = result
                    expression = result
                    hasResult = true
                }
                override fun onError(errorMessage: String) {
                    textViewExpression.text = errorMessage
                }
            })
        }
    }

    private fun newNumber() {
        if (hasResult) {
            expression = ""
            hasResult = false
        }
        lastIsNotNumber = false
    }

    private fun newNotNumber() {
        hasResult = false
        if (lastIsNotNumber) {
            del()
        }
        lastIsNotNumber = true
    }

    private fun add(value: String) {
        expression += value
        textViewExpression.text = expression
    }

    override fun onClick(view: View?) {
        if (expression.equals("0")) {
            expression = ""
        }
        when (view?.id) {
            R.id.button0 -> {
                newNumber()
                add("0")
            }
            R.id.button1 -> {
                newNumber()
                add("1")
            }
            R.id.button2 -> {
                newNumber()
                add("2")
            }
            R.id.button3 -> {
                newNumber()
                add("3")
            }
            R.id.button4 -> {
                newNumber()
                add("4")
            }
            R.id.button5 -> {
                newNumber()
                add("5")
            }
            R.id.button6 -> {
                newNumber()
                add("6")
            }
            R.id.button7 -> {
                newNumber()
                add("7")
            }
            R.id.button8 -> {
                newNumber()
                add("8")
            }
            R.id.button9 -> {
                newNumber()
                add("9")
            }
            R.id.buttonPlus -> {
                newNotNumber()
                add("+")
            }
            R.id.buttonMinus -> {
                newNotNumber()
                add("-")
            }
            R.id.buttonMulti -> {
                newNotNumber()
                add("*")
            }
        }
        Log.d("Test", textViewExpression.text as String)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(HAS_RESULT, hasResult)
        outState.putString(EXPRESSION, expression)
        outState.putBoolean(LAST_IS_NOT_NUMBER, lastIsNotNumber)
        super.onSaveInstanceState(outState)

    }
}
