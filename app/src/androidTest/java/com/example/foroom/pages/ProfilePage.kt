package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.tap
import org.hamcrest.Matcher

class ProfilePage {

    val changePasswordItem: Matcher<View> = withId(R.id.changePasswordItem)
    val changeLanguageItem: Matcher<View> = withId(R.id.changeLanguageItem)
    val signOutItem: Matcher<View> = withId(R.id.signOutItem)

    fun clickSignOut() = onView(signOutItem).tap()
}
