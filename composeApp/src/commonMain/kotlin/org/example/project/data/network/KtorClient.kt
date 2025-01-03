package org.example.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds


class KtorClient : PurchasesDataSource {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    private val BASE_URL = "https://cyberprot.ru/shopping/v2"

    override suspend fun getAutentificationKey(): Response {
        return try {
            withTimeout(3.seconds) {
                val response = httpClient.get("$BASE_URL/CreateTestKey?")
                Response(response.body(), null)
            }
        } catch (ex: kotlinx.coroutines.TimeoutCancellationException) {
            Response(null,  "ERROR:Request timeout after 3 seconds")
        }
        catch (ex: UnresolvedAddressException){
            Response(null,  "ERROR:No internet connection")
        }
    }
}