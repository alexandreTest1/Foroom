package com.example.foroom.pages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.input
import com.example.foroom.Helper.swiper
import com.example.foroom.Helper.tap
import com.example.foroom.data.Constants.SWIPE_DURATION_MS
import com.example.foroom.data.Constants.SWIPE_END_PERCENT
import com.example.foroom.data.Constants.SWIPE_START_PERCENT
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ConversationPage {

    val messagesRecyclerView: Matcher<View> = withId(R.id.messagesRecyclerView)
    val messageInput: Matcher<View> = withId(R.id.messageInput)
    val messageEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(messageInput))
    val sendMessageButton: Matcher<View> = withId(R.id.sendMessageButton)

    fun message(text: String): Matcher<View> =
        allOf(withId(DesignR.id.messageTextView), withText(text), isDescendantOfA(messagesRecyclerView))

    // the sender name is shown above the message text, in the same message bubble
    fun messageFromSender(text: String, senderName: String): Matcher<View> =
        allOf(message(text), hasSibling(hasDescendant(allOf(withId(DesignR.id.userNameTextView), withText(senderName)))))

    fun enterMessage(text: String) = onView(messageEditText).input(text)

    fun clickSend() = onView(sendMessageButton).tap()

    // newest messages are at the bottom, so dragging the finger down shows older messages;
    // the swipe points are calculated from the message list position, so they fit every screen size
    fun swipeToOlderMessages() {
        val location = IntArray(2)
        var height = 0
        onView(messagesRecyclerView).check { view, _ ->
            view.getLocationOnScreen(location)
            height = view.height
        }
        val start = location[1] + height * SWIPE_START_PERCENT / 100
        val end = location[1] + height * SWIPE_END_PERCENT / 100
        swiper(start, end, SWIPE_DURATION_MS)
    }

    // the newest message is the first item of the list
    fun scrollToNewestMessages() {
        onView(messagesRecyclerView).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }
}
