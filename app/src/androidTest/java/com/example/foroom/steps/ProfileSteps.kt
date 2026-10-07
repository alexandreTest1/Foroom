package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants.SCREEN_TIMEOUT_SEC
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.LoginPage
import com.example.foroom.pages.ProfilePage

class ProfileSteps {

    private val chatsPage = ChatsPage()
    private val profilePage = ProfilePage()
    private val loginPage = LoginPage()

    fun openProfilePage(): ProfileSteps {
        chatsPage.clickProfileTab()
        onView(profilePage.changePasswordItem).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        onView(profilePage.changeLanguageItem).check(matches(isDisplayed()))
        onView(profilePage.signOutItem).check(matches(isDisplayed()))
        return this
    }

    fun signOut(): ProfileSteps {
        profilePage.clickSignOut()
        onView(loginPage.logInButton).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        return this
    }
}
