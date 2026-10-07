package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.isDisplayedWithin
import com.example.foroom.Helper.waitUntilMatches
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants.MAX_SWIPES
import com.example.foroom.data.Constants.NETWORK_TIMEOUT_SEC
import com.example.foroom.data.Constants.SCREEN_TIMEOUT_SEC
import com.example.foroom.data.Constants.VISIBILITY_CHECK_SEC
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.ConversationPage
import org.junit.Assert.assertFalse

class ConversationSteps {

    private val chatsPage = ChatsPage()
    private val conversationPage = ConversationPage()

    fun verifyConversationIsOpened(chatName: String): ConversationSteps {
        onView(chatsPage.openedChatName).waitUntilVisible(NETWORK_TIMEOUT_SEC)
            .check(matches(withText(chatName)))
        onView(conversationPage.messageInput).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        // the send button becomes enabled when the chat is connected
        onView(conversationPage.sendMessageButton).waitUntilMatches(isEnabled(), NETWORK_TIMEOUT_SEC)
        return this
    }

    fun sendMessage(text: String): ConversationSteps {
        conversationPage.enterMessage(text)
        conversationPage.clickSend()
        return this
    }

    fun verifyMessageIsDisplayed(text: String): ConversationSteps {
        onView(conversationPage.message(text)).waitUntilVisible(NETWORK_TIMEOUT_SEC)
        return this
    }

    fun verifyMessageAndSender(text: String, senderName: String): ConversationSteps {
        onView(conversationPage.messageFromSender(text, senderName)).waitUntilVisible(NETWORK_TIMEOUT_SEC)
        return this
    }

    // every message is checked before the next one is sent, so the messages stay in order
    fun sendMessages(count: Int, textPrefix: String): ConversationSteps {
        for (number in 1..count) {
            val text = "$textPrefix $number"
            sendMessage(text)
            verifyMessageIsDisplayed(text)
        }
        return this
    }

    fun verifyMessageIsNotVisible(text: String): ConversationSteps {
        assertFalse(
            "Message should not be visible: $text",
            conversationPage.message(text).isDisplayedWithin(VISIBILITY_CHECK_SEC)
        )
        return this
    }

    // swipes a limited number of times, so a missing message fails the test instead of looping forever
    fun swipeToMessage(text: String): ConversationSteps {
        for (attempt in 1..MAX_SWIPES) {
            if (conversationPage.message(text).isDisplayedWithin(VISIBILITY_CHECK_SEC)) return this
            conversationPage.swipeToOlderMessages()
        }
        onView(conversationPage.message(text)).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        return this
    }

    fun scrollToNewestMessages(): ConversationSteps {
        conversationPage.scrollToNewestMessages()
        return this
    }
}
