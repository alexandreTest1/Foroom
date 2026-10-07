package com.example.foroom.Helper

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

/**
 * Wait until the view matches the given matcher (for example isEnabled())
 */
fun ViewInteraction.waitUntilMatches(matcher: Matcher<View>, timeoutSec: Long): ViewInteraction {
    val endTime = System.currentTimeMillis() + timeoutSec * 1000

    do {
        try {
            check(matches(matcher))
            return this
        } catch (_: Throwable) {
            Thread.sleep(50)
        }
    } while (System.currentTimeMillis() < endTime)

    throw TimeoutException()
}

/**
 * Is the view displayed within the given time? Returns false instead of failing
 */
fun Matcher<View>.isDisplayedWithin(timeoutSec: Long): Boolean {
    return try {
        onView(this).waitUntilMatches(isDisplayed(), timeoutSec)
        true
    } catch (_: TimeoutException) {
        false
    }
}
