package com.serioussem.phgim.school.app
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ClassScheduleRepository
) : ViewModel() {

    val pageHtml = mutableStateOf("empty")



    fun fetchJournal() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    pageHtml.value =
                        (repository.fetchCurrentWeekClassSchedule().data?.daysOfWeek?.get(0) ?: "error").toString()
                } catch (e: Exception) {
                    pageHtml.value = e.message.toString()
                }
            }
        }
    }

}