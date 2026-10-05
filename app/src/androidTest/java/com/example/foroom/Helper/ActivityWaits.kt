package com.example.foroom.Helper

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import java.util.concurrent.TimeoutException

/**
 * Perform action and wait until the current activity is recreated (for example after a language change)
 */
fun waitForActivityRecreated(timeoutSec: Long, action: () -> Unit) {
    val oldActivity = getResumedActivity()
    action()

    val endTime = System.currentTimeMillis() + timeoutSec * 1000
    do {
        val currentActivity = getResumedActivity()
        if (currentActivity != null && currentActivity !== oldActivity) return
        Thread.sleep(50)
    } while (System.currentTimeMillis() < endTime)

    throw TimeoutException()
}

private fun getResumedActivity(): Activity? {
    var activity: Activity? = null
    getInstrumentation().runOnMainSync {
        activity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).firstOrNull()
    }
    return activity
}
