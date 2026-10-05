package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.waitForActivityRecreated
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.ProfilePage
import com.example.foroom.steps.LoginSteps.Companion.NETWORK_TIMEOUT_SEC
import com.example.foroom.steps.LoginSteps.Companion.SCREEN_TIMEOUT_SEC
import org.hamcrest.Matchers.allOf

class ProfileSteps {

    private val chatsPage = ChatsPage()
    private val profilePage = ProfilePage()
    private val changePasswordPage = ChangePasswordPage()
    private val changeLanguagePage = ChangeLanguagePage()

    fun openProfilePage(): ProfileSteps {
        chatsPage.clickProfileTab()
        onView(profilePage.changePasswordItem).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        onView(profilePage.changeLanguageItem).check(matches(isDisplayed()))
        onView(profilePage.signOutItem).check(matches(isDisplayed()))
        return this
    }

    fun clickChangePassword(): ProfileSteps {
        profilePage.clickChangePassword()
        onView(changePasswordPage.passwordInput).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        onView(changePasswordPage.repeatPasswordInput).check(matches(isDisplayed()))
        return this
    }

    fun enterNewPassword(password: String): ProfileSteps {
        changePasswordPage.enterPassword(password)
        changePasswordPage.enterRepeatPassword(password)
        return this
    }

    fun clickConfirm(): ProfileSteps {
        changePasswordPage.clickConfirm()
        return this
    }

    fun openChangeLanguage(): ProfileSteps {
        profilePage.clickChangeLanguage()
        onView(changeLanguagePage.languageButtonGeo).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        onView(changeLanguagePage.languageButtonEng).check(matches(isDisplayed()))
        return this
    }

    fun selectGeorgian(): ProfileSteps {
        waitForActivityRecreated(NETWORK_TIMEOUT_SEC) { changeLanguagePage.clickGeorgian() }
        return this
    }

    fun selectEnglish(): ProfileSteps {
        waitForActivityRecreated(NETWORK_TIMEOUT_SEC) { changeLanguagePage.clickEnglish() }
        return this
    }

    fun verifyProfileLabels(changeLanguageText: String, signOutText: String): ProfileSteps {
        onView(allOf(profilePage.changeLanguageTitle, withText(changeLanguageText))).waitUntilVisible(NETWORK_TIMEOUT_SEC)
        onView(profilePage.signOutTitle).check(matches(withText(signOutText)))
        return this
    }
}
