package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants.NETWORK_TIMEOUT_SEC
import com.example.foroom.data.Constants.SCREEN_TIMEOUT_SEC
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage

class ChatSteps {

    private val chatsPage = ChatsPage()
    private val createChatPage = CreateChatPage()

    fun verifyHomeScreenIsDisplayed(): ChatSteps {
        onView(chatsPage.navBar).waitUntilVisible(NETWORK_TIMEOUT_SEC)
        onView(chatsPage.searchChatInput).check(matches(isDisplayed()))
        return this
    }

    fun openCreateChatPage(): ChatSteps {
        chatsPage.clickCreateChatTab()
        onView(createChatPage.chatNameInput).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        onView(createChatPage.chatImageChooser).check(matches(isDisplayed()))
        onView(createChatPage.createChatButton).check(matches(isDisplayed()))
        return this
    }

    fun enterChatName(chatName: String): ChatSteps {
        createChatPage.enterChatName(chatName)
        return this
    }

    fun selectChatImage(index: Int): ChatSteps {
        onView(createChatPage.loadedChatImageChooser).waitUntilVisible(NETWORK_TIMEOUT_SEC)
        createChatPage.clickChatImage(index)
        onView(createChatPage.selectedChatImage(index)).check(matches(isDisplayed()))
        return this
    }

    fun clickCreateChat(): ChatSteps {
        createChatPage.clickCreateChat()
        return this
    }

    fun verifyCreatedChatIsOpened(chatName: String): ChatSteps {
        onView(chatsPage.openedChatName).waitUntilVisible(NETWORK_TIMEOUT_SEC)
            .check(matches(withText(chatName)))
        return this
    }

    fun closeChat(): ChatSteps {
        chatsPage.clickCloseOpenedChat()
        onView(chatsPage.searchChatInput).waitUntilVisible(SCREEN_TIMEOUT_SEC)
        return this
    }

    fun searchChat(chatName: String): ChatSteps {
        chatsPage.enterSearchText(chatName)
        return this
    }

    fun verifyChatIsInList(chatName: String): ChatSteps {
        onView(chatsPage.chatCard(chatName)).waitUntilVisible(NETWORK_TIMEOUT_SEC)
        return this
    }

    fun openChat(chatName: String): ChatSteps {
        chatsPage.clickOpenChat(chatName)
        return this
    }
}
