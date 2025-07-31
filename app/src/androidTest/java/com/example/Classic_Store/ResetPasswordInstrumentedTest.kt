package com.example.Classic_Store

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.Classic_Store.view.ResetPasswordActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ResetPasswordInstrumentedTest {

    @get:Rule
    val testRule = ActivityScenarioRule(ResetPasswordActivity::class.java)

    @Test
    fun checkSendResetEmail_success() {
        onView(withId(R.id.emailInput)).perform(typeText("ram@gmail.com"))
        closeSoftKeyboard()

        onView(withId(R.id.sendEmailButton)).check(matches(isDisplayed())).perform(click())

        Thread.sleep(3000)

        onView(withId(R.id.resultForgot)).check(matches(withText("OK")))
    }
}
