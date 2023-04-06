package com.github.ljts42.hw10_test

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.ljts42.hw10_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val calculator = Calculator()
    private lateinit var binding: ActivityMainBinding
    private var hasDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            listOf(
                button0,
                button1,
                button2,
                button3,
                button4,
                button5,
                button6,
                button7,
                button8,
                button9
            ).forEach { initNumber(it) }

            listOf(
                buttonAdd, buttonSub, buttonMul, buttonDiv
            ).forEach { initOperation(it) }

            buttonDot.setOnClickListener {
                if (prevText.text.isEmpty() || prevText.text.last() in "+-*/") {
                    if (resultText.text.last() == '-') {
                        resultText.append("0")
                    }
                    if (!hasDot) {
                        resultText.append(buttonDot.text)
                    }
                } else {
                    prevText.append(resultText.text)
                    resultText.text = "0."
                }
                hasDot = true
            }

            buttonClear.setOnClickListener {
                prevText.text = ""
                resultText.text = "0"
                hasDot = false
            }

            buttonDel.setOnClickListener {
                if (resultText.text.length == 1) {
                    if (prevText.text.isEmpty()) {
                        resultText.text = "0"
                    } else if (prevText.text.last() in "+-*/") {
                        resultText.text = prevText.text.last().toString()
                        prevText.text = prevText.text.dropLast(1)
                    } else {
                        resultText.text = prevText.text
                        prevText.text = ""
                        hasDot = buttonDot.text in resultText.text
                    }
                } else {
                    if (resultText.text.last() == '.') {
                        hasDot = false
                    }
                    resultText.text = resultText.text.dropLast(1)
                }
            }

            buttonCopy.setOnClickListener {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(resultText.text, resultText.text)
                clipboard.setPrimaryClip(clip)
            }

            buttonEq.setOnClickListener {
                if (prevText.text.isNotEmpty()) {
                    if (prevText.text.last() in "+-*/") {
                        if (resultText.text.last() == '.') {
                            resultText.append("0")
                        }
                        val left = prevText.text.dropLast(1).toString()
                            .replace(buttonDot.text.toString(), ".")
                        val right =
                            resultText.text.toString().replace(buttonDot.text.toString(), ".")
                        resultText.text = calculator.compute(
                            left, prevText.text.last(), right
                        ).replace(".", buttonDot.text.toString())
                    } else {
                        resultText.text = prevText.text
                    }
                    hasDot = '.' in resultText.text
                    prevText.text = ""
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(HAS_DOT, hasDot)
        outState.putString(PREV_LINE, binding.prevText.text.toString())
        outState.putString(RESULT_LINE, binding.resultText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        hasDot = savedInstanceState.getBoolean(HAS_DOT)
        binding.prevText.text = savedInstanceState.getString(PREV_LINE)
        binding.resultText.text = savedInstanceState.getString(RESULT_LINE)
    }

    companion object {
        private const val HAS_DOT = "ljts42.MainActivity.has_dot"
        private const val PREV_LINE = "ljts42.MainActivity.prev_line"
        private const val RESULT_LINE = "ljts42.MainActivity.result_line"
    }

    private fun initNumber(button: Button) {
        button.setOnClickListener {
            if (binding.resultText.text.toString() == "0") {
                binding.resultText.text = button.text
            } else if (binding.prevText.text.isEmpty() || binding.prevText.text.last() in "+-*/") {
                binding.resultText.append(button.text)
            } else {
                binding.prevText.append(binding.resultText.text.last().toString())
                binding.resultText.text = button.text
            }
        }
    }

    private fun initOperation(button: Button) {
        button.setOnClickListener {
            if (binding.resultText.text.toString() == "-") {
                binding.resultText.text = "0"
            }
            if (binding.prevText.text.isEmpty()) {
                binding.prevText.text = binding.resultText.text
                hasDot = false
            } else if (binding.prevText.text.last() in "+-*/") {
                if (binding.resultText.text.last() == '.') {
                    binding.resultText.append("0")
                }
                val left = binding.prevText.text.dropLast(1).toString()
                    .replace(binding.buttonDot.text.toString(), ".")
                val right = binding.resultText.text.toString()
                    .replace(binding.buttonDot.text.toString(), ".")
                binding.prevText.text = calculator.compute(
                    left, binding.prevText.text.last(), right
                ).replace(".", binding.buttonDot.text.toString())
            }
            binding.resultText.text = button.text
        }
    }
}