package com.serioussem.phgim.school.presentation.ui.screens.login
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.serioussem.phgim.school.core.BaseViewModel
import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.domain.core.Result
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.presentation.navigation.Screen
import com.serioussem.phgim.school.utils.LocalStorageKeys.LOGIN_KEY
import com.serioussem.phgim.school.utils.LocalStorageKeys.PASSWORD_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ClassScheduleRepository,
    private val storage: LocalStorage,
) : BaseViewModel<LoginScreenContract.Event, LoginScreenContract.State>() {

    override fun setInitialState(): LoginScreenContract.State {
        return LoginScreenContract.State(
            login = "",
            password = "",
            isEnabledSignInButton = false,
            isLoading = false,
            error = null
        )
    }

    override fun <T> setEvent(event: LoginScreenContract.Event, data: T) {
        when (event) {
            LoginScreenContract.Event.SIGN_IN -> {
                signIn(data as NavController)
            }

            LoginScreenContract.Event.CLOSE_DIALOG -> {
                closeDialog()

            }

            LoginScreenContract.Event.CHANGE_LOGIN -> {
                changeLogin(loginValue = data as String)
            }

            LoginScreenContract.Event.CHANGE_PASSWORD -> {
                changePassword(passwordValue = data as String)
            }
        }
    }

    private fun closeDialog() {
        setState {
            copy(
                login = this.login,
                password = this.password,
                isEnabledSignInButton = this.isEnabledSignInButton,
                isLoading = false,
                error = null
            )
        }
    }

    private fun changeLogin(loginValue: String) {
        setState {
            copy(
                login = loginValue,
                password = this.password,
                isEnabledSignInButton = getSignInButtonStatus(),
                isLoading = false,
                error = null
            )
        }
    }

    private fun changePassword(passwordValue: String) {
        setState {
            copy(
                login = this.login,
                password = passwordValue,
                isEnabledSignInButton = getSignInButtonStatus(),
                isLoading = false,
                error = null
            )
        }
    }

    private fun saveLoginAndPassword() {
        try {
            storage.saveData(LOGIN_KEY, viewState.value.login)
            storage.saveData(PASSWORD_KEY, viewState.value.password)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun signIn(navController: NavController) {
        setState {
            copy(
                login = this.login,
                password = this.password,
                isEnabledSignInButton = this.isEnabledSignInButton,
                isLoading = true,
                error = null
            )
        }
        try {
            viewModelScope.launch {
                try {
                    val login = viewState.value.login
                    val password = viewState.value.password
                    when (val responseResult = repository.signIn(login, password)) {

                        is Result.Success -> {
                            saveLoginAndPassword()
                            navController.navigate(Screen.ClassSchedule.route)
                        }

                        is Result.Error -> {
                            setState {
                                copy(
                                    login = this.login,
                                    password = this.password,
                                    isEnabledSignInButton = this.isEnabledSignInButton,
                                    isLoading = false,
                                    error = responseResult.message
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    setState {
                        copy(
                            login = this.login,
                            password = this.password,
                            isEnabledSignInButton = this.isEnabledSignInButton,
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
            }
        } catch (e: Exception) {
            setState {
                copy(
                    login = this.login,
                    password = this.password,
                    isEnabledSignInButton = this.isEnabledSignInButton,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun getSignInButtonStatus(): Boolean {
        return viewState.value.login.isNotEmpty() && viewState.value.password.isNotEmpty()
    }


}