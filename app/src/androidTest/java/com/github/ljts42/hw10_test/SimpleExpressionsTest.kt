package com.github.ljts42.hw10_test

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SimpleExpressionsTest {
    private lateinit var device: UiDevice

    @Before
    fun startMainActivityFromHomeScreen() {
        device = UiDevice.getInstance(getInstrumentation())

        val context = getInstrumentation().context
        val intent = context.packageManager.getLaunchIntentForPackage(CALCULATOR_PACKAGE)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(CALCULATOR_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
    }

    @Test
    fun testTwoPlusTwo() {
        device.findObject(By.res(CALCULATOR_PACKAGE, "button2")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonAdd")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "button2")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonEq")).click()
        assertEquals("4", device.findObject(By.res(CALCULATOR_PACKAGE, "resultText")).text)
    }

    @Test
    fun testDivisionByZero() {
        device.findObject(By.res(CALCULATOR_PACKAGE, "button9")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonDiv")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "button0")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonEq")).click()
        assertEquals(
            "division by zero", device.findObject(By.res(CALCULATOR_PACKAGE, "resultText")).text
        )
    }

    @Test
    fun testChangeOrientation() {
        device.findObject(By.res(CALCULATOR_PACKAGE, "button1")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "button2")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "button3")).click()

        device.setOrientationLeft()
        device.wait(Until.hasObject(By.pkg(CALCULATOR_PACKAGE).depth(0)), LAUNCH_TIMEOUT)

        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonSub")).click()

        device.setOrientationRight()
        device.wait(Until.hasObject(By.pkg(CALCULATOR_PACKAGE).depth(0)), LAUNCH_TIMEOUT)

        device.findObject(By.res(CALCULATOR_PACKAGE, "button4")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "button5")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "button6")).click()

        device.setOrientationNatural()
        device.wait(Until.hasObject(By.pkg(CALCULATOR_PACKAGE).depth(0)), LAUNCH_TIMEOUT)

        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonEq")).click()
        assertEquals("-333", device.findObject(By.res(CALCULATOR_PACKAGE, "resultText")).text)
    }

    @Test
    fun testClearTheMainAnswer() {
        device.findObject(By.res(CALCULATOR_PACKAGE, "button7")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonMul")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "button6")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonEq")).click()
        device.findObject(By.res(CALCULATOR_PACKAGE, "buttonClear")).click()
        assertEquals("0", device.findObject(By.res(CALCULATOR_PACKAGE, "resultText")).text)
    }

    companion object {
        private const val CALCULATOR_PACKAGE = "com.github.ljts42.hw10_test"
        private const val LAUNCH_TIMEOUT = 5000L
    }
}