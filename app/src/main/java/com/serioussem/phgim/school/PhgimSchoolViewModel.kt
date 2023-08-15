package com.serioussem.phgim.school

import android.provider.Telephony.Carriers.PASSWORD
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.phgim.school.data.retrofit.RetrofitService
import com.serioussem.phgim.school.utils.URL.BASE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Cookie
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class PhgimSchoolViewModel @Inject constructor(
    private val service: RetrofitService
) : ViewModel() {

    val pageHtml = mutableStateOf("empty")

    companion object {
        const val USER_NAME = "JuliaNabok"
        const val PASSWORD = "qw1234"
//        const val USER_NAME = "Mariana-Nabok"
//        const val PASSWORD = "Nabok2009"
    }

    private suspend fun fetchCsrfToken(): String {
        try {
            val csrfTokenResponse = service.fetchCsrfToken().body() ?: "empty page"
            val csrfTokenDocument = Jsoup.parse(csrfTokenResponse)
            val csrfTokenElement =
                csrfTokenDocument.selectFirst("input[name=csrfmiddlewaretoken]")
            return csrfTokenElement?.attr("value") ?: ""
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun fetchPupilId(
        username: String,
        password: String,
        csrfToken: String
    ): String {
        try {
            val endpointResponse = service.fetchPupilId(username, password, csrfToken)
            val endpointString = endpointResponse.toString()
            val endpointPattern = Regex("url=(https?://[^,\\s}]+)")
            val endpointMatchResult = endpointPattern.find(endpointString)
            val endpoint = endpointMatchResult?.groupValues?.get(1)?.replace(BASE_URL, "") ?: ""
            val idPattern = "/(\\w+)/([\\d]+)".toRegex()
            val idMatchResult = idPattern.find(endpoint)
            val userType = idMatchResult?.groupValues?.get(1) ?: ""
            return if (userType == "pupil") {
                val pupilId = idMatchResult?.groupValues?.get(2) ?: ""
                pupilId
            } else {
                val endpointResponseBody = endpointResponse.body() ?: "empty page"
                val parentPageDocument = Jsoup.parse(endpointResponseBody)
                val userTypeElement =
                    parentPageDocument.getElementsByClass("user_type_1").first()
                val pupilTypeElement = userTypeElement?.attr("href") ?: ""
                val pupilIdMatchResult = idPattern.find(pupilTypeElement)
                val pupilId = pupilIdMatchResult?.groupValues?.get(2) ?: ""
                pupilId
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun fetchJournal() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val token = fetchCsrfToken()
                    val pupilId = fetchPupilId(USER_NAME, PASSWORD, token)
                    pageHtml.value = service.fetchJournal(pupilId).body().toString()
                } catch (e: Exception) {
                    pageHtml.value = e.message.toString()
                }
            }
        }
    }

}