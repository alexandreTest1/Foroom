package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.input
import com.example.foroom.Helper.tap
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class ChangePasswordPage {

    val repeatPasswordInput: Matcher<View> = withId(R.id.repeatPasswordInput)
    val passwordInput: Matcher<View> = allOf(withId(R.id.passwordInput), hasSibling(repeatPasswordInput))

    val passwordEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(passwordInput))
    val repeatPasswordEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(repeatPasswordInput))

    val actionButton: Matcher<View> = withId(DesignR.id.actionButton)

    fun enterPassword(password: String) = onView(passwordEditText).input(password)

    fun enterRepeatPassword(password: String) = onView(repeatPasswordEditText).input(password)

    fun clickConfirm() = onView(actionButton).tap()
}
