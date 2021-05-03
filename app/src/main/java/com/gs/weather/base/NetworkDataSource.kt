package com.gs.weather.base

import com.gs.weather.utils.Result
import com.gs.weather.utils.Result.Error
import com.gs.weather.utils.Result.Success
import com.gs.weather.utils.exception.BadServerResponse
import com.gs.weather.utils.exception.NoConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

interface NetworkDataSource {
    suspend fun <T, R, P> safeApiCall(
        call: suspend () -> Response<T>,
        params: P,
        transform: (T, P) -> R
    ): Result<R> {
        try {
            val response = withContext(Dispatchers.IO) { call.invoke() }
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    withContext(Dispatchers.Default) {
                        Success(transform(body, params))
                    }
                } else {
                    Error(BadServerResponse)
                }
            } else {
                Error(BadServerResponse)
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return Error(NoConnection)
        } catch (unknown: Exception) {
            unknown.printStackTrace()
            return Error(BadServerResponse)
        }
    }
}