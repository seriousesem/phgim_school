package com.serioussem.phgim.school.presentation.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel  @Inject constructor(
    private val repository: ClassScheduleRepository
) : ViewModel() {

    fun loadAndSaveClassSchedule(){
        viewModelScope.launch {
            repository.loadAndSaveClassSchedule()
        }
    }

    fun navigateToNextScreen(){

    }

}