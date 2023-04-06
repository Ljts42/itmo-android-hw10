package com.github.ljts42.hw10_test

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculationUnitTest {
    private val calc = Calculator()

    @Test
    fun addition_isCorrect() {
        assertEquals("17", calc.compute("9", '+', "8"))
    }

    @Test
    fun subtraction_isCorrect() {
        assertEquals("1", calc.compute("7", '-', "6"))
    }

    @Test
    fun multiplication_isCorrect() {
        assertEquals("20", calc.compute("5", '*', "4"))
    }

    @Test
    fun division_isCorrect() {
        assertEquals("1,5", calc.compute("3", '/', "2"))
    }

    @Test
    fun division_byZero_isCorrect() {
        assertEquals("division by zero", calc.compute("1", '/', "0"))
    }

    @Test
    fun wrongFormat_leftArgument_isCorrect() {
        assertEquals("wrong first number format", calc.compute("a", '+', "2"))
    }

    @Test
    fun wrongFormat_rightArgument_isCorrect() {
        assertEquals("wrong second number format", calc.compute("1", '*', "b"))
    }

    @Test
    fun unknownSign_isCorrect() {
        assertEquals("wrong sign", calc.compute("3", '%', "4"))
    }
}