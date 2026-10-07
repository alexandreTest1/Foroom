package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.data.Constants.ACADEMY_QUESTION
import com.example.foroom.data.Constants.ADDITIONAL_MESSAGE
import com.example.foroom.data.Constants.ADDITIONAL_MESSAGES_COUNT
import com.example.foroom.data.Constants.DRINK_MESSAGE
import com.example.foroom.data.Constants.GREETING
import com.example.foroom.data.Constants.JOHN_WEEK_CHAT
import com.example.foroom.data.Constants.OWN_CHAT
import com.example.foroom.data.Constants.PASSWORD_A
import com.example.foroom.data.Constants.PASSWORD_B
import com.example.foroom.data.Constants.REPLY
import com.example.foroom.data.Constants.SHARED_CHAT
import com.example.foroom.data.Constants.USER_A
import com.example.foroom.data.Constants.USER_B
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.ConversationSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import com.example.foroom.steps.TestDataSteps
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class ConversationTests {

    // every test starts signed out, on the login screen
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
    private val chatSteps = ChatSteps()
    private val conversationSteps = ConversationSteps()
    private val profileSteps = ProfileSteps()
    private val testDataSteps = TestDataSteps()

    // runs once per test run, so every test can also be run alone
    @Before
    fun prepareTestData() {
        if (testDataPrepared) return

        testDataSteps
            .registerUserIfMissing(USER_A, PASSWORD_A)
            .registerUserIfMissing(USER_B, PASSWORD_B)

        loginSteps.signIn(USER_A, PASSWORD_A)
        chatSteps.verifyHomeScreenIsDisplayed()

        testDataSteps
            .createChatIfMissing(JOHN_WEEK_CHAT)
            .createChatIfMissing(OWN_CHAT)
            .createChatIfMissing(SHARED_CHAT)

        profileSteps
            .openProfilePage()
            .signOut()

        testDataPrepared = true
    }

    @Test
    fun sendMessageInJohnWeek_messageStaysAfterReopeningChat() {
        val message = "$DRINK_MESSAGE ${System.currentTimeMillis()}"

        loginSteps.signIn(USER_A, PASSWORD_A)

        chatSteps
            .verifyHomeScreenIsDisplayed()
            .searchChat(JOHN_WEEK_CHAT)
            .verifyChatIsInList(JOHN_WEEK_CHAT)
            .openChat(JOHN_WEEK_CHAT)

        conversationSteps
            .verifyConversationIsOpened(JOHN_WEEK_CHAT)
            .sendMessage(message)
            .verifyMessageIsDisplayed(message)

        chatSteps
            .closeChat()
            .searchChat(JOHN_WEEK_CHAT)
            .verifyChatIsInList(JOHN_WEEK_CHAT)
            .openChat(JOHN_WEEK_CHAT)

        conversationSteps
            .verifyConversationIsOpened(JOHN_WEEK_CHAT)
            .verifyMessageIsDisplayed(message)
    }

    @Test
    fun sendQuestionInOwnChat_questionIsDisplayed() {
        val question = "$ACADEMY_QUESTION ${System.currentTimeMillis()}"

        loginSteps.signIn(USER_A, PASSWORD_A)

        chatSteps
            .verifyHomeScreenIsDisplayed()
            .searchChat(OWN_CHAT)
            .verifyChatIsInList(OWN_CHAT)
            .openChat(OWN_CHAT)

        conversationSteps
            .verifyConversationIsOpened(OWN_CHAT)
            .sendMessage(question)
            .verifyMessageIsDisplayed(question)
    }

    @Test
    fun continueConversationWithAnotherAccount_bothUsersSeeEachOthersMessages() {
        val suffix = System.currentTimeMillis()
        val greeting = "$GREETING $suffix"
        val reply = "$REPLY $suffix"

        loginSteps.signIn(USER_A, PASSWORD_A)

        chatSteps
            .verifyHomeScreenIsDisplayed()
            .searchChat(SHARED_CHAT)
            .verifyChatIsInList(SHARED_CHAT)
            .openChat(SHARED_CHAT)

        conversationSteps
            .verifyConversationIsOpened(SHARED_CHAT)
            .sendMessage(greeting)
            .verifyMessageIsDisplayed(greeting)
            .sendMessages(ADDITIONAL_MESSAGES_COUNT, "$ADDITIONAL_MESSAGE $suffix")

        chatSteps.closeChat()

        profileSteps
            .openProfilePage()
            .signOut()

        loginSteps.signIn(USER_B, PASSWORD_B)

        chatSteps
            .verifyHomeScreenIsDisplayed()
            .searchChat(SHARED_CHAT)
            .verifyChatIsInList(SHARED_CHAT)
            .openChat(SHARED_CHAT)

        conversationSteps
            .verifyConversationIsOpened(SHARED_CHAT)
            .verifyMessageIsNotVisible(greeting)
            .swipeToMessage(greeting)
            .verifyMessageAndSender(greeting, USER_A)
            .scrollToNewestMessages()
            .sendMessage(reply)
            .verifyMessageIsDisplayed(reply)

        chatSteps.closeChat()

        profileSteps
            .openProfilePage()
            .signOut()

        loginSteps.signIn(USER_A, PASSWORD_A)

        chatSteps
            .verifyHomeScreenIsDisplayed()
            .searchChat(SHARED_CHAT)
            .verifyChatIsInList(SHARED_CHAT)
            .openChat(SHARED_CHAT)

        conversationSteps
            .verifyConversationIsOpened(SHARED_CHAT)
            .scrollToNewestMessages()
            .verifyMessageAndSender(reply, USER_B)
    }

    companion object {
        private var testDataPrepared = false
    }
}
