package com.github.ljts42.hw10_test

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class Calculator {
    fun compute(first: String, sign: Char, second: String): String {
        if (sign !in "+-*/") {
            return "wrong sign"
        }
        val left = try {
            BigDecimal(first)
        } catch (e: NumberFormatException) {
            return "wrong first number format"
        }
        val right = try {
            BigDecimal(second)
        } catch (e: NumberFormatException) {
            return "wrong second number format"
        }
        return try {
            DecimalFormat("0.#####").format(
                when (sign) {
                    '*' -> left.multiply(right)
                    '-' -> left.subtract(right)
                    '/' -> left.divide(right, 5, RoundingMode.HALF_UP)
                    else -> left.plus(right)
                }
            )
        } catch (e: ArithmeticException) {
            "division by zero"
        }
    }
}