package com.serioussem.phgim.school
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.phgim.school.data.retrofit.RetrofitService
import com.serioussem.phgim.school.utils.URL.BASE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class PhgimSchoolViewModel @Inject constructor(
    private val service: RetrofitService
): ViewModel() {

    val pageHtml = mutableStateOf("empty")

    fun fetchSessionId(){
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO){
                    val csrfTokenResponse = service.fetchCsrfToken().body() ?: "empty page"
                    val csrfTokenDocument = Jsoup.parse(csrfTokenResponse)
                    val csrfTokenElement = csrfTokenDocument.select("input[name=csrfmiddlewaretoken]").firstOrNull()
                    val csrfToken = csrfTokenElement?.attr("value") ?: ""
                    val endpointResponse = service.fetchEndPoint("JuliaNabok", "qw1234", csrfToken).toString()
//                    val endpointResponse = service.fetchEndPoint("Mariana-Nabok", "Nabok2009", csrfToken).toString()
                    val urlPattern = Regex("url=(https?://[^,\\s}]+)")
                    val matchResult = urlPattern.find(endpointResponse)
                    val url = matchResult?.groupValues?.get(1) ?: ""
                    val endpoint = url.replace(BASE_URL, "")
                    pageHtml.value = endpoint

                }
            }catch (e: Exception){
                pageHtml.value = e.message.toString()
            }
        }
    }

}