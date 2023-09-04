package com.serioussem.phgim.school.core
import android.content.Context
import com.serioussem.phgim.school.R
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class ResponseHandler @Inject constructor(
    private val context: Context,
) {
    suspend fun <T> fetch(
        apiRequest: suspend () -> Response<T>?
    ): Response<T> {
        try {
            val response = apiRequest.invoke()
            val body = response?.body()
            if (response?.isSuccessful == true && body != null) {
                return response
            }
            throw CustomErrors.AppError(message = context.getString(R.string.server_connection_error_message))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException ->
                    throw CustomErrors.AppError(message = context.getString(R.string.network_connection_error_message))

                else -> {
                    throw CustomErrors.AppError(message = e.message.toString())
                }
            }
        }
    }
}