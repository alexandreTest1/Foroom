package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.RegistrationPage
import com.example.foroom.steps.LoginSteps.Companion.NETWORK_TIMEOUT_SEC
import com.example.foroom.steps.LoginSteps.Companion.SCREEN_TIMEOUT_SEC

class RegistrationSteps {

    private val registrationPage = RegistrationPage()

    fun verifyRegistrationScreenIsDisplayed(): RegistrationSteps {
        onView(registrationPage.repeatPasswordInput).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        onView(registrationPage.userNameInput).check(matches(isDisplayed()))
        onView(registrationPage.passwordInput).check(matches(isDisplayed()))
        onView(registrationPage.avatarList).check(matches(isDisplayed()))
        onView(registrationPage.signUpButton).check(matches(isDisplayed()))
        return this
    }

    fun enterUserName(userName: String): RegistrationSteps {
        registrationPage.enterUserName(userName)
        return this
    }

    fun enterPassword(password: String): RegistrationSteps {
        registrationPage.enterPassword(password)
        return this
    }

    fun enterRepeatPassword(password: String): RegistrationSteps {
        registrationPage.enterRepeatPassword(password)
        return this
    }

    fun selectAvatar(index: Int): RegistrationSteps {
        onView(registrationPage.loadedAvatarList).waitUntilVisible(NETWORK_TIMEOUT_SEC)
        registrationPage.clickAvatar(index)
        onView(registrationPage.selectedAvatar(index)).check(matches(isDisplayed()))
        return this
    }

    fun clickSignUp(): RegistrationSteps {
        registrationPage.clickSignUp()
        return this
    }

    fun verifyHomeScreenIsDisplayed(): RegistrationSteps {
        onView(registrationPage.homeNavBar).waitUntilVisible(NETWORK_TIMEOUT_SEC)
        return this
    }
}
