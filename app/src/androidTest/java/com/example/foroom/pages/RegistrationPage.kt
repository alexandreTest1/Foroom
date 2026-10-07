package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
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

class RegistrationPage {

    private val logInTextView: Matcher<View> = withId(R.id.logInTextView)

    val userNameInput: Matcher<View> = allOf(withId(R.id.userNameInput), hasSibling(logInTextView))
    val passwordInput: Matcher<View> = allOf(withId(R.id.passwordInput), hasSibling(logInTextView))
    val repeatPasswordInput: Matcher<View> = withId(R.id.repeatPasswordInput)
    val signUpButton: Matcher<View> = allOf(withId(R.id.signUpButton), hasSibling(logInTextView))

    val userNameEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(userNameInput))
    val passwordEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(passwordInput))
    val repeatPasswordEditText: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(repeatPasswordInput))

    val avatarList: Matcher<View> = withId(R.id.listView)

    val loadedAvatarList: Matcher<View> = allOf(avatarList, object : BoundedMatcher<View, ImageChooserListView>(ImageChooserListView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("avatars are loaded")
        }

        override fun matchesSafely(item: ImageChooserListView) = item.isChoosingEnabled
    })

    val homeNavBar: Matcher<View> = withId(R.id.navBar)

    fun avatar(index: Int): Matcher<View> =
        withIndex(allOf(isAssignableFrom(ImageChooserItemView::class.java), isDescendantOfA(avatarList)), index)

    fun selectedAvatar(index: Int): Matcher<View> = allOf(avatar(index), object : BoundedMatcher<View, ImageChooserItemView>(ImageChooserItemView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("avatar is selected")
        }

        override fun matchesSafely(item: ImageChooserItemView) = item.isImageSelected
    })

    fun enterUserName(userName: String) = onView(userNameEditText).input(userName)

    fun enterPassword(password: String) = onView(passwordEditText).input(password)

    fun enterRepeatPassword(password: String) = onView(repeatPasswordEditText).input(password)

    fun clickAvatar(index: Int) = onView(avatar(index)).perform(click())

    fun clickSignUp() = onView(signUpButton).tap()
}
