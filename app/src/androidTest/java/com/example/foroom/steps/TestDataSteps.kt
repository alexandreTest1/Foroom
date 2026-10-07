package com.example.foroom.steps

import com.example.foroom.Helper.isDisplayedWithin
import com.example.foroom.data.Constants.AVATAR_INDEX
import com.example.foroom.data.Constants.CHAT_IMAGE_INDEX
import com.example.foroom.data.Constants.SCREEN_TIMEOUT_SEC
import com.example.foroom.data.Constants.SEARCH_TIMEOUT_SEC
import com.example.foroom.pages.ChatsPage

// prepares the accounts and chats on the current device, only creates what is missing
class TestDataSteps {

    private val chatsPage = ChatsPage()
    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()
    private val chatSteps = ChatSteps()
    private val profileSteps = ProfileSteps()

    fun registerUserIfMissing(userName: String, password: String): TestDataSteps {
        loginSteps.signIn(userName, password)

        if (!chatsPage.navBar.isDisplayedWithin(SCREEN_TIMEOUT_SEC)) {
            loginSteps.clickSignUp()
            registrationSteps
                .verifyRegistrationScreenIsDisplayed()
                .enterUserName(userName)
                .enterPassword(password)
                .enterRepeatPassword(password)
                .selectAvatar(AVATAR_INDEX)
                .clickSignUp()
                .verifyHomeScreenIsDisplayed()
        }

        profileSteps
            .openProfilePage()
            .signOut()
        return this
    }

    fun createChatIfMissing(chatName: String): TestDataSteps {
        chatSteps.searchChat(chatName)

        if (!chatsPage.chatCard(chatName).isDisplayedWithin(SEARCH_TIMEOUT_SEC)) {
            chatSteps
                .openCreateChatPage()
                .enterChatName(chatName)
                .selectChatImage(CHAT_IMAGE_INDEX)
                .clickCreateChat()
                .verifyCreatedChatIsOpened(chatName)
                .closeChat()
        }
        return this
    }
}
