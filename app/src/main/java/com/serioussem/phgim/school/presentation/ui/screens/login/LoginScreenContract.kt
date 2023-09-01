package com.serioussem.phgim.school.presentation.ui.screens.login
import com.serioussem.phgim.school.core.ViewEvent
import com.serioussem.phgim.school.core.ViewState

class LoginScreenContract {
    enum class Event : ViewEvent {
       SIGN_IN, CLOSE_DIALOG, CHANGE_LOGIN, CHANGE_PASSWORD
    }
    data class State(
        val login: String = "",
        val password: String = "",
        val isEnabledSignInButton: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState


}