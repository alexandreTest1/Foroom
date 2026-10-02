package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.LoginPage
import org.hamcrest.Matchers.not

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

    fun verifyUserNameErrorIsDisplayed(): LoginSteps {
        onView(loginPage.userNameError).waitUntilVisible(NETWORK_TIMEOUT_SEC)
            .check(matches(not(withText(""))))
        return this
    }

    fun verifyPasswordErrorIsDisplayed(): LoginSteps {
        onView(loginPage.passwordError).waitUntilVisible(NETWORK_TIMEOUT_SEC)
            .check(matches(not(withText(""))))
        return this
    }

    companion object {
        const val SCREEN_TIMEOUT_SEC = 10L
        const val NETWORK_TIMEOUT_SEC = 30L
    }
}
