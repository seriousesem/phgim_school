package com.serioussem.phgim.school.presentation.ui.screens.splash
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.presentation.navigation.Screen
import com.serioussem.phgim.school.utils.LocalStorageKeys.LOGIN_KEY
import com.serioussem.phgim.school.utils.LocalStorageKeys.PASSWORD_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: ClassScheduleRepository,
    private val storage: LocalStorage,
) : ViewModel() {

    var loadingProgressState by mutableFloatStateOf(0.0F)
        private set

    init {

        loadAndSaveClassSchedule()
    }

    private fun loadAndSaveClassSchedule() {
        try {
            //        viewModelScope.launch {
//            repository.loadAndSaveClassSchedule()
//        }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun navigateToNextScreen(navController: NavController) {
        val isAuthorized = checkAuthorization()
        startLoadingProgress(isAuthorized)
        if (!isAuthorized) {
            navController.navigate(Screen.Login.route)
        } else {
            navController.navigate(Screen.ClassSchedule.route)
        }
    }

    private fun checkAuthorization(): Boolean {
        try {
            val login = storage.loadData<String>(LOGIN_KEY, defaultValue = "")
            val password = storage.loadData<String>(PASSWORD_KEY, defaultValue = "")
            return login.isNotEmpty() && password.isNotEmpty()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun startLoadingProgress(authorizationStatus: Boolean) {
        val progressDelay = if (authorizationStatus) 100L else 25L
        while (loadingProgressState < 1) {
            loadingProgressState += 0.01F
            delay(progressDelay)
        }
    }

}