package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants.SCREEN_TIMEOUT_SEC
import com.example.foroom.pages.LoginPage

class LoginSteps {

    private val loginPage = LoginPage()

    fun verifyLoginScreenIsDisplayed(): LoginSteps {
        onView(loginPage.logInButton).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        onView(loginPage.userNameInput).check(matches(isDisplayed()))
        onView(loginPage.passwordInput).check(matches(isDisplayed()))
        onView(loginPage.signUpButton).check(matches(isDisplayed()))
        return this
    }

    fun enterUserName(userName: String): LoginSteps {
        loginPage.enterUserName(userName)
        return this
    }

    fun enterPassword(password: String): LoginSteps {
        loginPage.enterPassword(password)
        return this
    }

    fun clickLogIn(): LoginSteps {
        loginPage.clickLogIn()
        return this
    }

    fun clickSignUp(): LoginSteps {
        loginPage.clickSignUp()
        return this
    }

    fun signIn(userName: String, password: String): LoginSteps {
        verifyLoginScreenIsDisplayed()
        enterUserName(userName)
        enterPassword(password)
        clickLogIn()
        return this
    }
}
