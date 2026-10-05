package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.input
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.withIndex
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class CreateChatPage {

    val chatNameInput: Matcher<View> = withId(R.id.chatNameInput)
    val chatNameEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(chatNameInput))
    val createChatButton: Matcher<View> = withId(R.id.createChatButton)

    val chatImageChooser: Matcher<View> = withId(R.id.chatImageChooser)

    val loadedChatImageChooser: Matcher<View> = allOf(chatImageChooser, object : BoundedMatcher<View, ImageChooserListView>(ImageChooserListView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("chat images are loaded")
        }

        override fun matchesSafely(item: ImageChooserListView) = item.isChoosingEnabled
    })

    fun chatImage(index: Int): Matcher<View> =
        withIndex(allOf(isAssignableFrom(ImageChooserItemView::class.java), isDescendantOfA(chatImageChooser)), index)

    fun selectedChatImage(index: Int): Matcher<View> = allOf(chatImage(index), object : BoundedMatcher<View, ImageChooserItemView>(ImageChooserItemView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("chat image is selected")
        }

        override fun matchesSafely(item: ImageChooserItemView) = item.isImageSelected
    })

    fun enterChatName(chatName: String) = onView(chatNameEditText).input(chatName)

    fun clickChatImage(index: Int) = onView(chatImage(index)).perform(click())

    fun clickCreateChat() = onView(createChatButton).tap()
}
