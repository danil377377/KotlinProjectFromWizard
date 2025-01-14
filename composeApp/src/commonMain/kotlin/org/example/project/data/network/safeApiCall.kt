package org.example.project.data.network

import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.withTimeout
import org.example.project.data.network.model.Response
import kotlin.time.Duration.Companion.seconds

suspend fun <T : Response> safeApiCall(apiCall: suspend () -> T): T {
    return try {
        withTimeout(3.seconds) {
            apiCall()
        }
    } catch (ex: kotlinx.coroutines.TimeoutCancellationException) {
        Response().apply { resultCode = -3 } as T
    } catch (ex: UnresolvedAddressException) {
        Response().apply { resultCode = -1 } as T
    }
}