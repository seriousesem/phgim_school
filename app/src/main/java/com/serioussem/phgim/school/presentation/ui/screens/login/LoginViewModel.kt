package com.serioussem.phgim.school.presentation.ui.screens.login
import androidx.lifecycle.ViewModel
import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ClassScheduleRepository,
    private val storage: LocalStorage,
) : ViewModel() {



}