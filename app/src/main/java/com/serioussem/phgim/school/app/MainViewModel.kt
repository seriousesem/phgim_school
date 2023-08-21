package com.serioussem.phgim.school.app
import android.provider.Telephony.Carriers.PASSWORD
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.phgim.school.data.jsoup.JsoupParser
import com.serioussem.phgim.school.data.repository.ClassScheduleRepositoryImpl.Companion.USER_NAME
import com.serioussem.phgim.school.data.retrofit.RetrofitService
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.utils.URL.BASE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
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
                        (repository.fetchClassSchedule("2023-08-14").data?.daysOfWeek?.get(0) ?: "error").toString()
                } catch (e: Exception) {
                    pageHtml.value = e.message.toString()
                }
            }
        }
    }

}