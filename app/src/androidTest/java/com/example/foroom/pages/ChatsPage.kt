package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.input
import com.example.foroom.Helper.tap
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class ChatsPage {

    val navBar: Matcher<View> = withId(R.id.navBar)
    val createChatTab: Matcher<View> = withId(R.id.homeNavigationCreateChat)
    val profileTab: Matcher<View> = withId(R.id.homeNavigationProfile)

    val searchChatInput: Matcher<View> = withId(R.id.searchChatInput)
    val searchEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(searchChatInput))
    val chatsRecyclerView: Matcher<View> = withId(R.id.chatsRecyclerView)

    private val messageInput: Matcher<View> = withId(R.id.messageInput)
    val openedChatHeader: Matcher<View> = allOf(withId(R.id.chatHeaderView), hasSibling(messageInput))
    val openedChatName: Matcher<View> = allOf(withId(DesignR.id.chatNameTextView), isDescendantOfA(openedChatHeader))
    val openedChatCloseButton: Matcher<View> = allOf(withId(R.id.closeButton), hasSibling(messageInput))

    fun chatCard(chatName: String): Matcher<View> =
        allOf(withId(DesignR.id.chatTitleTextView), withText(chatName), isDescendantOfA(chatsRecyclerView))

    // a chat is opened with the send button on its card
    fun openChatButton(chatName: String): Matcher<View> =
        allOf(withId(DesignR.id.sendMessageButton), hasSibling(chatCard(chatName)))

    fun clickCreateChatTab() = onView(createChatTab).tap()

    fun clickProfileTab() = onView(profileTab).tap()

    fun enterSearchText(text: String) = onView(searchEditText).input(text)

    fun clickCloseOpenedChat() = onView(openedChatCloseButton).tap()

    fun clickOpenChat(chatName: String) = onView(openChatButton(chatName)).tap()
}
