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

class LoginPage {

    val logInButton: Matcher<View> = withId(R.id.logInButton)

    val userNameInput: Matcher<View> = allOf(withId(R.id.userNameInput), hasSibling(logInButton))
    val passwordInput: Matcher<View> = allOf(withId(R.id.passwordInput), hasSibling(logInButton))
    val signUpButton: Matcher<View> = allOf(withId(R.id.signUpButton), hasSibling(logInButton))

    val userNameEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(userNameInput))
    val passwordEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(passwordInput))

    val userNameError: Matcher<View> = allOf(withId(DesignR.id.descriptionTextView), isDescendantOfA(userNameInput))
    val passwordError: Matcher<View> = allOf(withId(DesignR.id.descriptionTextView), isDescendantOfA(passwordInput))

    fun enterUserName(userName: String) = onView(userNameEditText).input(userName)

    fun enterPassword(password: String) = onView(passwordEditText).input(password)

    fun clickLogIn() = onView(logInButton).tap()

    fun clickSignUp() = onView(signUpButton).tap()
}
