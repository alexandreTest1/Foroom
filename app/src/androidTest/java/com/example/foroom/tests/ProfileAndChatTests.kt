package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {

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
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()

    @Test
    fun changePassword_logInWithNewPassword_opensHomeScreen() {
        loginSteps
            .verifyLoginScreenIsDisplayed()
            .enterUserName(USER_NAME)
            .enterPassword(PASSWORD)
            .clickLogIn()

        chatSteps.verifyHomeScreenIsDisplayed()

        profileSteps
            .openProfilePage()
            .clickChangePassword()
            .enterNewPassword(NEW_PASSWORD)
            .clickConfirm()

        loginSteps
            .verifyLoginScreenIsDisplayed()
            .enterUserName(USER_NAME)
            .enterPassword(NEW_PASSWORD)
            .clickLogIn()

        chatSteps.verifyHomeScreenIsDisplayed()

        // restore the original password so the test can be run again
        profileSteps
            .openProfilePage()
            .clickChangePassword()
            .enterNewPassword(PASSWORD)
            .clickConfirm()

        loginSteps.verifyLoginScreenIsDisplayed()
    }

    @Test
    fun changeLanguage_georgianToEnglishAndBack_showsTranslatedLabels() {
        loginSteps
            .verifyLoginScreenIsDisplayed()
            .enterUserName(USER_NAME)
            .enterPassword(PASSWORD)
            .clickLogIn()

        chatSteps.verifyHomeScreenIsDisplayed()

        profileSteps
            .openProfilePage()
            .openChangeLanguage()
            .selectGeorgian()
            .verifyProfileLabels(CHANGE_LANGUAGE_GEO, SIGN_OUT_GEO)
            .openChangeLanguage()
            .selectEnglish()
            .verifyProfileLabels(CHANGE_LANGUAGE_ENG, SIGN_OUT_ENG)
            .openChangeLanguage()
            .selectGeorgian()
            .verifyProfileLabels(CHANGE_LANGUAGE_GEO, SIGN_OUT_GEO)
    }

    @Test
    fun createChat_searchByName_showsChatInList() {
        val chatName = "$FULL_NAME ${System.currentTimeMillis()}"

        loginSteps
            .verifyLoginScreenIsDisplayed()
            .enterUserName(USER_NAME)
            .enterPassword(PASSWORD)
            .clickLogIn()

        chatSteps
            .verifyHomeScreenIsDisplayed()
            .openCreateChatPage()
            .enterChatName(chatName)
            .selectChatImage(CHAT_IMAGE_INDEX)
            .clickCreateChat()
            .verifyCreatedChatIsOpened(chatName)
            .closeChat()
            .searchChat(chatName)
            .verifyChatIsInList(chatName)
    }

    companion object {
        const val USER_NAME = "alexatester"
        const val PASSWORD = "Cxraati#"
        const val NEW_PASSWORD = "Cxraati#New1"
        const val FULL_NAME = "Aleksandre Tevzadze"
        const val CHAT_IMAGE_INDEX = 1
        const val CHANGE_LANGUAGE_GEO = "ენის შეცვლა"
        const val SIGN_OUT_GEO = "გამოსვლა"
        const val CHANGE_LANGUAGE_ENG = "Change Language"
        const val SIGN_OUT_ENG = "Sign Out"
    }
}
