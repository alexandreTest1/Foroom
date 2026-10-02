package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class LoginAndRegistrationTests {

    @get:Rule(order = 0)
    val clearUserDataRule = object : TestWatcher() {
        override fun starting(description: Description) {
            val userDataStore: ForoomUserDataStore = GlobalContext.get().get()
            runBlocking { userDataStore.clearUserData() }
        }
    }

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()

    @Test
    fun validUserNameAndInvalidPassword_showsPasswordError() {
        loginSteps
            .verifyLoginScreenIsDisplayed()
            .enterUserName(EXISTING_USER_NAME)
            .enterPassword(WRONG_PASSWORD)
            .clickLogIn()
            .verifyPasswordErrorIsDisplayed()
    }

    @Test
    fun invalidUserNameAndInvalidPassword_showsUserNameAndPasswordErrors() {
        loginSteps
            .verifyLoginScreenIsDisplayed()
            .enterUserName(NOT_EXISTING_USER_NAME)
            .enterPassword(WRONG_PASSWORD)
            .clickLogIn()
            .verifyUserNameErrorIsDisplayed()
            .verifyPasswordErrorIsDisplayed()
    }

    @Test
    fun registrationWithUniqueUserName_opensHomeScreen() {
        val uniqueUserName = "user${System.currentTimeMillis()}"

        loginSteps
            .verifyLoginScreenIsDisplayed()
            .clickSignUp()

        registrationSteps
            .verifyRegistrationScreenIsDisplayed()
            .enterUserName(uniqueUserName)
            .enterPassword(VALID_PASSWORD)
            .enterRepeatPassword(VALID_PASSWORD)
            .selectAvatar(AVATAR_INDEX)
            .clickSignUp()
            .verifyHomeScreenIsDisplayed()
    }

    companion object {
        const val EXISTING_USER_NAME = "student"
        const val NOT_EXISTING_USER_NAME = "no_such_user_987654"
        const val WRONG_PASSWORD = "WrongPass123"
        const val VALID_PASSWORD = "Test12345"
        const val AVATAR_INDEX = 1
    }
}
